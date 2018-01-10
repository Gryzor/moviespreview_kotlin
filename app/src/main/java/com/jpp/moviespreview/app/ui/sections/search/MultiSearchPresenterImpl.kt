package com.jpp.moviespreview.app.ui.sections.search

import com.jpp.moviespreview.app.domain.MultiSearchPage
import com.jpp.moviespreview.app.domain.MultiSearchParam
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.domain.MultiSearchPage as DomainSearchPage
import com.jpp.moviespreview.app.domain.MultiSearchResult as DomainSearchResult

/**
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
    }


    private fun executeUseCase(query: String) {
        val param = MultiSearchParam(query, 1)
        interactorDelegate.executeBackgroundJob(
                {
                    //TODO show loading if needed

                    multiSearchContext.onGoingQueryParam = param

                    useCase.execute(param)
                },
                {

                    processIfIsDataFromOngoingCall(it, {
                        val uiResults = mapper.convertDomainResultPageInUiResultPage(it, getPosterImageConfiguration(), getProfileImageConfiguration())
                        viewInstance.showResults(uiResults.results)
                        listenQueryUpdates()
                    })


                    processIfIsError(it, {
                        //TODO manage error
                    })

                }
        )
    }

    private fun listenQueryUpdates() {
        querySubmitManager
                .linkQueryTextView(
                        viewInstance.getQueryTextView(),
                        { query ->
                            executeUseCase(query)
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


    private fun getProfileImageConfiguration() = interactorDelegate.findProfileImageConfigurationForHeight(multiSearchContext.getProfileImageConfig()!!, viewInstance.getTargetMultiSearchResultImageSize())

    private fun getPosterImageConfiguration() = interactorDelegate.findPosterImageConfigurationForWidth(multiSearchContext.getPosterImageConfigs()!!, viewInstance.getTargetMultiSearchResultImageSize())

}