package com.jpp.moviespreview.app.ui.sections.main.movies

import com.jpp.moviespreview.app.ui.Error
import com.jpp.moviespreview.app.ui.interactors.BackgroundExecutor
import com.jpp.moviespreview.app.ui.interactors.ImageConfigurationInteractor
import com.jpp.moviespreview.app.ui.interactors.PaginationInteractor
import com.jpp.moviespreview.app.util.extentions.whenNotNull

/**
 * Created by jpp on 2/19/18.
 */
class MoviesPresenterImpl(private val moviesContextInteractor: MoviesContextInteractor,
                          private val backgroundExecutor: BackgroundExecutor,
                          private var interactor: MoviesPresenterInteractor,
                          private val paginationInteractor: PaginationInteractor,
                          private val imageConfigInteractor: ImageConfigurationInteractor) : MoviesPresenter {


    private lateinit var moviesView: MoviesView
    private val moviesData by lazy { MoviesData({ observeData() }) }

    override fun linkView(moviesView: MoviesView) {
        this.moviesView = moviesView
        executeBlockIfConfigIsCompleted {
            with(moviesContextInteractor.getAllMoviePages()) {
                when (size) {
                    0 -> configureInteractorAndGetFirstMoviePage()
                    else -> forEach { moviesView.showMoviePage(it) }
                }
            }
        }
    }


    override fun getNextMoviePage() {
        executeBlockIfConfigIsCompleted {
            paginationInteractor.managePagination(
                    { moviesContextInteractor.getAllMoviePages() },
                    { moviesView.showEndOfPaging() },
                    {
                        showLoadingIfNeeded()
                        backgroundExecutor.executeBackgroundJob { interactor.retrieveMoviePage(it) }
                    }
            )
        }
    }

    private fun configureInteractorAndGetFirstMoviePage() {
        with(moviesContextInteractor) {
            val selectedImageConfig = imageConfigInteractor.findPosterImageConfigurationForWidth(getPosterImageConfigs(), moviesView.getScreenWidth())
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
        if (moviesContextInteractor.isConfigCompleted()) {
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
                    moviesContextInteractor.addMoviePage(it)
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