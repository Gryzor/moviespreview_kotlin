package com.jpp.moviespreview.app.ui.sections.search

import com.jpp.moviespreview.app.domain.MultiSearchPage
import com.jpp.moviespreview.app.domain.MultiSearchParam
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.domain.MultiSearchPage as DomainSearchPage
import com.jpp.moviespreview.app.domain.MultiSearchResult as DomainSearchResult

/**
 * Created by jpp on 1/6/18.
 */
class MultiSearchPresenterImpl(private val moviesContext: MoviesContext,
                               private val interactorDelegate: MultiSearchPresenterInteractor,
                               private val mapper: DomainToUiDataMapper,
                               private val querySubmitManager: QuerySubmitManager,
                               private val useCase: UseCase<MultiSearchParam, MultiSearchPage>) : MultiSearchPresenter {


    private lateinit var viewInstance: MultiSearchView

    override fun linkView(multiSearchView: MultiSearchView) {
        viewInstance = multiSearchView
        querySubmitManager
                .linkQueryTextView(
                        multiSearchView.getQueryTextView(),
                        { query ->
                            executeUseCase(query)
                        })
    }


    private fun executeUseCase(query: String) {
        val param = MultiSearchParam(query, 1)
        interactorDelegate.executeBackgroundJob(
                {
                    //TODO show loading if needed
                    useCase.execute(param)
                },
                {
                    processSearchResult(it)

                }
        )
    }

    private fun processSearchResult(result: DomainSearchPage?) {
        if (result != null) {
            val uiResults = mapper.convertDomainResultPageInUiResultPage(result, getPosterImageConfiguration(), getProfileImageConfiguration())
            viewInstance.showResults(uiResults.results)
        } else {
            //TODO what do we do here?
        }
    }


    private fun getProfileImageConfiguration() = interactorDelegate.findProfileImageConfigurationForHeight(moviesContext.profileImageConfig!!, viewInstance.getTargetMultiSearchResultImageSize())

    private fun getPosterImageConfiguration() = interactorDelegate.findPosterImageConfigurationForWidth(moviesContext.posterImageConfig!!, viewInstance.getTargetMultiSearchResultImageSize())

}