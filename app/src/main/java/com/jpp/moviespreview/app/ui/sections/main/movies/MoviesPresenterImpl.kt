package com.jpp.moviespreview.app.ui.sections.main.movies

import com.jpp.moviespreview.app.ui.Error
import com.jpp.moviespreview.app.ui.Movie
import com.jpp.moviespreview.app.ui.MoviesContextHandler
import com.jpp.moviespreview.app.ui.PosterImageConfiguration
import com.jpp.moviespreview.app.ui.interactors.BackgroundExecutor
import com.jpp.moviespreview.app.ui.interactors.ImageConfigurationManager
import com.jpp.moviespreview.app.ui.interactors.PaginationController
import com.jpp.moviespreview.app.util.extentions.whenFalse
import com.jpp.moviespreview.app.util.extentions.whenNotNull

/**
 * [MoviesPresenter] implementation.
 * Responsibilities:
 *  1 - Retrieve movies pages.
 *  2 - Store the pages in the context.
 *  3 - Notify the UI about new data to show.
 * Created by jpp on 2/19/18.
 */
class MoviesPresenterImpl(private val moviesContextHandler: MoviesContextHandler,
                          private val backgroundExecutor: BackgroundExecutor,
                          private var interactor: MoviesPresenterInteractor,
                          private val paginationController: PaginationController,
                          private val imageConfigManager: ImageConfigurationManager) : MoviesPresenter {


    private lateinit var moviesView: MoviesView
    private val moviesData by lazy { MoviesData({ observeData() }) }

    override fun linkView(moviesView: MoviesView) {
        this.moviesView = moviesView
        executeBlockIfConfigIsCompleted {
            with(moviesContextHandler.getAllMoviePages()) {
                when (size) {
                    0 -> configureInteractorAndGetFirstMoviePage()
                    else -> forEach { moviesView.showMoviePage(it) }
                }
            }
        }
    }


    override fun getNextMoviePage() {
        executeBlockIfConfigIsCompleted {
            paginationController.controlPagination(
                    { moviesContextHandler.getAllMoviePages() },
                    { moviesView.showEndOfPaging() },
                    {
                        showLoadingIfNeeded()
                        backgroundExecutor.executeBackgroundJob { interactor.retrieveMoviePage(it) }
                    }
            )
        }
    }

    override fun onMovieSelected(movie: Movie) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private fun configureInteractorAndGetFirstMoviePage() {
        with(moviesContextHandler) {
            getMovieGenres()?.let {
                val selectedImageConfig = getPosterImageConfiguration()
                interactor.configure(moviesData, it, selectedImageConfig)
                getNextMoviePage()
            } ?: run {
                // should never happen, since this is executed with executeBlockIfConfigIsCompleted()
                throw IllegalStateException("Movies context should be completed at this point")
            }
        }
    }


    /**
     * Finds and return the [PosterImageConfiguration] for the current screen
     * configuration.
     */
    private fun getPosterImageConfiguration(): PosterImageConfiguration {
        with(moviesContextHandler) {
            getPosterImageConfigs()?.let {
                return imageConfigManager.findPosterImageConfigurationForWidth(it, moviesView.getScreenWidth())
            } ?: run {
                // should never happen, since this is executed with executeBlockIfConfigIsCompleted()
                throw IllegalStateException("Movies context should be completed at this point")
            }
        }
    }


    /**
     * Executes the given [block] if the movies context is completed.
     * If not completed, redirects the app to the splash screen to
     * load all the needed resources into the context.
     */
    private fun executeBlockIfConfigIsCompleted(block: () -> Unit) {
        if (moviesContextHandler.isConfigCompleted()) {
            block()
        } else {
            moviesView.backToSplashScreen()
        }
    }


    /**
     * Asks the [MoviesView] to show loading view if there
     * are no movies currently on screen.
     */
    private fun showLoadingIfNeeded() {
        with(moviesView) {
            whenFalse(isShowingMovies(), { showLoading() })
        }
    }


    /**
     * Observes the data that will be updated by the Command.
     */
    private fun observeData() {
        with(moviesData) {
            whenNotNull(lastMoviePage, {
                backgroundExecutor.executeUiJob {
                    moviesContextHandler.addMoviePage(it)
                    moviesView.showMoviePage(it)
                }
            })
            whenNotNull(error, {
                backgroundExecutor.executeUiJob {
                    processError(it)
                }
            })
        }
    }

    /**
     * Process the errors detected while retrieving the movies.
     */
    private fun processError(error: Error) {
        with(error) {
            if (type == Error.NO_CONNECTION) {
                moviesView.showNotConnectedToNetwork()
            } else {
                moviesView.showUnexpectedError()
            }
        }
    }

}