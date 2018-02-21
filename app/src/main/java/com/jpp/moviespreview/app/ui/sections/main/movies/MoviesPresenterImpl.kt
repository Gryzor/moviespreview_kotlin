package com.jpp.moviespreview.app.ui.sections.main.movies

import com.jpp.moviespreview.app.ui.Error
import com.jpp.moviespreview.app.ui.interactors.BackgroundExecutor
import com.jpp.moviespreview.app.ui.interactors.ImageConfigurationManager
import com.jpp.moviespreview.app.ui.interactors.PaginationController
import com.jpp.moviespreview.app.util.extentions.whenNotNull

/**
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

    private fun configureInteractorAndGetFirstMoviePage() {
        with(moviesContextHandler) {
            val selectedImageConfig = imageConfigManager.findPosterImageConfigurationForWidth(getPosterImageConfigs(), moviesView.getScreenWidth())
            interactor.configure(moviesData, getMovieGenres(), selectedImageConfig)
            getNextMoviePage()
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

    private fun showLoadingIfNeeded() {
        with(moviesView) {
            if (isShowingMovies()) {
                showLoading()
            }
        }
    }


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