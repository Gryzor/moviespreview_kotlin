package com.jpp.moviespreview.app.ui.sections.search

import com.jpp.moviespreview.app.ui.Error
import com.jpp.moviespreview.app.ui.MultiSearchResult
import com.jpp.moviespreview.app.ui.PosterImageConfiguration
import com.jpp.moviespreview.app.ui.ProfileImageConfiguration
import com.jpp.moviespreview.app.ui.interactors.BackgroundExecutor
import com.jpp.moviespreview.app.ui.interactors.ImageConfigurationManager
import com.jpp.moviespreview.app.ui.interactors.PaginationController
import com.jpp.moviespreview.app.util.extentions.whenNotNull
import com.jpp.moviespreview.app.util.extentions.whenTrue
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
                               private val querySubmitManager: QuerySubmitManager,
                               private val backgroundExecutor: BackgroundExecutor,
                               private val imageConfigManager: ImageConfigurationManager,
                               private val paginationController: PaginationController,
                               private val searchFlowResolver: SearchFlowResolver,
                               private val interactor: MultiSearchInteractor) : MultiSearchPresenter {

    private lateinit var viewInstance: MultiSearchView
    private val searchData by lazy { MultiSearchData({ observeData() }) }

    override fun linkView(multiSearchView: MultiSearchView) {
        viewInstance = multiSearchView
        listenQueryUpdates()
        with(multiSearchContext) {
            whenTrue(multiSearchContext.hasSearchPages()) {
                viewInstance.showResults(getAllSearchResults())
            }
        }
    }


    override fun getNextSearchPage() {
        paginationController.controlPagination(
                { multiSearchContext.getAllPages() },
                { viewInstance.showEndOfPaging() },
                { configureInteractorAndExecuteSearch(multiSearchContext.getAllPages().last().query, it) }
        )
    }

    override fun clearLastSearch() {
        multiSearchContext.clearPages()
        viewInstance.clearPages()
        viewInstance.clearSearch()
        listenQueryUpdates()
    }

    override fun onItemSelected(selectedItem: MultiSearchResult) {
        if (selectedItem.movieDetails != null) {
            multiSearchContext.setSelectedMovie(selectedItem.movieDetails)
            searchFlowResolver.showMovieDetails()
        }
    }


    /**
     * Adds a listener to the [querySubmitManager] in order to detect new queries.
     * When a new query is detected, the use case is executed to retrieve the first
     * page of the query.
     */
    private fun listenQueryUpdates() {
        with(viewInstance) {
            querySubmitManager.linkQueryTextView(getQueryTextView(),
                    {
                        multiSearchContext.clearPages()
                        viewInstance.clearPages()
                        configureInteractorAndExecuteSearch(it)
                    })
        }
    }


    /**
     * Configures the interactor and retrieves the first page of the search.
     */
    private fun configureInteractorAndExecuteSearch(query: String, page: Int? = null) {
        with(multiSearchContext) {
            getUIMovieGenres()?.let {
                interactor.configure(searchData, it, getPosterImageConfiguration(), getProfileImageConfiguration())
                backgroundExecutor.executeBackgroundJob {
                    page?.let {
                        interactor.searchPage(query, page)
                    } ?: run {
                        interactor.searchFirstPage(query)
                    }
                }
            } ?: run {
                // should never happen, fail if it does
                throw IllegalStateException("Search context should be completed at this point")
            }
        }
    }


    /**
     * Observes the data that will be updated by the interactor.
     */
    private fun observeData() {
        with(searchData) {
            whenNotNull(lastSearchPage) {
                backgroundExecutor.executeUiJob {
                    with(multiSearchContext) {
                        addSearchPage(it)
                        viewInstance.showResults(getAllSearchResults())
                    }
                }
            }
            whenNotNull(error) {
                processError(it)
            }
        }
    }

    /**
     * Process the errors detected while retrieving the movies.
     */
    private fun processError(error: Error) {
        with(error) {
            when (type) {
                Error.NO_CONNECTION -> viewInstance.showNotConnectedToNetwork()
                else -> viewInstance.showUnexpectedError()
            }
        }
    }

    /**
     * Finds and return the [PosterImageConfiguration] for the current screen
     * configuration.
     */
    private fun getPosterImageConfiguration(): PosterImageConfiguration {
        with(multiSearchContext) {
            getPosterImageConfigs()?.let {
                return imageConfigManager.findPosterImageConfigurationForWidth(it, viewInstance.getTargetMultiSearchResultImageSize())
            } ?: run {
                // should never happen, fail if it does
                throw IllegalStateException("Search context should be completed at this point")
            }
        }
    }

    /**
     * Finds and return the [ProfileImageConfiguration] for the current screen
     * configuration.
     */
    private fun getProfileImageConfiguration(): ProfileImageConfiguration {
        with(multiSearchContext) {
            getProfileImageConfig()?.let {
                return imageConfigManager.findProfileImageConfigurationForHeight(it, viewInstance.getTargetMultiSearchResultImageSize())
            } ?: run {
                // should never happen, fail if it does
                throw IllegalStateException("Search context should be completed at this point")
            }
        }
    }
}