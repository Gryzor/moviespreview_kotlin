package com.jpp.moviespreview.app.ui.sections.search

import com.jpp.moviespreview.app.domain.MultiSearchPage
import com.jpp.moviespreview.app.domain.MultiSearchParam
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.MultiSearchResult
import com.jpp.moviespreview.app.domain.MultiSearchPage as DomainSearchPage
import com.jpp.moviespreview.app.domain.MultiSearchResult as DomainSearchResult

/**
 * [MultiSearchPresenter] implementation. Will add itself as listener of the [querySubmitManager]
 * in order to handle the queries executed.
 *
 * When the user makes a query, the presenter will execute the use case, will store the result
 * in [MultiSearchContext] in order to keep them in memory (to handle rotation for instance) and
 * will ask the [MultiSearchView] to show the result.
 *
 * Created by jpp on 1/6/18.
 */
class MultiSearchPresenterImpl(private val multiSearchContext: MultiSearchContext,
                               private val interactorDelegate: MultiSearchPresenterInteractor,
                               private val mapper: DomainToUiDataMapper,
                               private val querySubmitManager: QuerySubmitManager,
                               private val useCase: UseCase<MultiSearchParam, MultiSearchPage>) : MultiSearchPresenter {


    private lateinit var viewInstance: MultiSearchView

    override fun linkView(multiSearchView: MultiSearchView) {
        viewInstance = multiSearchView
        listenQueryUpdates()
        with(multiSearchContext) {
            if (hasSearchPages()) {
                viewInstance.showResults(getAllSearchResults())
            }
        }
    }


    override fun getNextSearchPage() {
        with(multiSearchContext) {
            if (interactorDelegate.isIdle()) {
                val param = createNextUseCaseParam(onGoingQueryParam!!.query)
                if (param != null) {
                    executeUseCase(param, {
                        viewInstance.appendResults(it)
                    })
                }
            }
        }
    }

    override fun clearLastSearch() {
        multiSearchContext.clearPages()
        viewInstance.clearPages()
    }


    private fun executeUseCase(param: MultiSearchParam, showResult: (List<MultiSearchResult>) -> Unit) {
        with(multiSearchContext) {
            if (multiSearchContext.onGoingQueryParam != param) {
                interactorDelegate.executeBackgroundJob(
                        {
                            //TODO show loading if needed
                            clearPagesInContextIfQueryChanged(multiSearchContext.onGoingQueryParam?.query, param.query)
                            multiSearchContext.onGoingQueryParam = param
                            useCase.execute(param)
                        },
                        {
                            // process only if is for the current query
                            processIfIsDataFromOngoingCall(it, {
                                val uiResults = mapper.convertDomainResultPageInUiResultPage(it, getPosterImageConfiguration(), getProfileImageConfiguration())
                                multiSearchContext.addSearchPage(uiResults)
                                showResult(uiResults.results)
                                listenQueryUpdates()
                            })


                            processIfIsError(it, {
                                //TODO manage error
                            })

                        }
                )
            }
        }
    }

    /**
     * Adds a listener to the [querySubmitManager] in order to detect new queries.
     * When a new query is detected, the use case is executed to retrieve the first
     * page of the query.
     */
    private fun listenQueryUpdates() {
        querySubmitManager
                .linkQueryTextView(
                        viewInstance.getQueryTextView(),
                        { query ->
                            executeUseCase(createNextUseCaseParam(query)!!, {
                                viewInstance.showResults(it)
                            })
                        })
    }


    private fun processIfIsDataFromOngoingCall(result: DomainSearchPage?, func: (DomainSearchPage) -> Unit) {
        if (multiSearchContext.onGoingQueryParam?.page == result?.page) {
            func(result!!) // only if result != null
        }
    }


    private fun processIfIsError(result: DomainSearchPage?, func: () -> Unit) {
        if (result == null) {
            func()
        }
    }

    private fun clearPagesInContextIfQueryChanged(queryInContext: String?, newQuery: String) {
        if (queryInContext != newQuery) {
            multiSearchContext.clearPages()
        }
    }

    private fun createNextUseCaseParam(query: String): MultiSearchParam? {
        //TODO create PaginationInteractor
        with(multiSearchContext) {
            var lastMPageIndex = 0 // by default, always get the first page
            var lastPage: com.jpp.moviespreview.app.ui.MultiSearchPage? = null

            if (getAllSearchPages().isNotEmpty()) {
                lastPage = getAllSearchPages().last()
                lastMPageIndex = lastPage.page
            }

            val nextPage = lastMPageIndex + 1

            if (lastPage != null && nextPage > lastPage.totalPages) {
                viewInstance.showEndOfPaging()
                return null
            }

            return MultiSearchParam(query, nextPage)
        }
    }

    private fun getProfileImageConfiguration() = interactorDelegate.findProfileImageConfigurationForHeight(multiSearchContext.getProfileImageConfig()!!, viewInstance.getTargetMultiSearchResultImageSize())

    private fun getPosterImageConfiguration() = interactorDelegate.findPosterImageConfigurationForWidth(multiSearchContext.getPosterImageConfigs()!!, viewInstance.getTargetMultiSearchResultImageSize())

}