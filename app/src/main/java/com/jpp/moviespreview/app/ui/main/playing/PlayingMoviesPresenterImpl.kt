package com.jpp.moviespreview.app.ui.main.playing

import android.annotation.SuppressLint
import android.support.annotation.VisibleForTesting
import com.jpp.moviespreview.app.domain.MoviesInTheaterInputParam
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.ImageConfiguration
import com.jpp.moviespreview.app.ui.MoviePage
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.interactors.PresenterInteractorDelegate
import com.jpp.moviespreview.app.domain.Genre as DomainGenre
import com.jpp.moviespreview.app.domain.MoviePage as DomainMoviePage

/**
 * Presenter implementation for the playing movies in theater section.
 *
 * Created by jpp on 10/23/17.
 */
class PlayingMoviesPresenterImpl(private val moviesContext: MoviesContext,
                                 private val interactorDelegate: PresenterInteractorDelegate,
                                 private val playingMoviesUseCase: UseCase<MoviesInTheaterInputParam, DomainMoviePage>,
                                 private val mapper: DomainToUiDataMapper) : PlayingMoviesPresenter {


    private lateinit var playingMoviesView: PlayingMoviesView
    private var targetScreenWidth: Int? = null

    // function to assign a target screen width
    private val assignTargetScreenWidth = { value: Int ->
        targetScreenWidth = value
        targetScreenWidth!!
    }

    override fun linkView(view: PlayingMoviesView) {
        playingMoviesView = view
        loadOrRetrieveMovies()
    }


    /**
     * Verifies if there are pages loaded into the context. If there are, loads those
     * pages into the screen. If not, it attempts to retrieve the new pages.
     */
    private fun loadOrRetrieveMovies() {
        with(moviesContext) {
            if (hasMoviePages()) {
                getAllMoviePages().forEach({
                    playingMoviesView.showMoviePage(it)
                })
            } else {
                getNextMoviePage()
            }
        }
    }


    @SuppressLint("VisibleForTests")
    override fun getNextMoviePage() {
        with(moviesContext) {
            if (isConfigCompleted()) {
                if (interactorDelegate.isIdle()) {
                    val param = createNextUseCaseParam()
                    if (param != null) {
                        executeUseCase(param)
                    }
                }
            } else {
                playingMoviesView.backToSplashScreen()
            }
        }
    }


    /**
     * Creates the [MoviesInTheaterInputParam] that is going to be used for the next
     * use case execution. If the scrolling has reached the last possible position,
     * it asks the view to show the end of page and returns null.
     */
    fun createNextUseCaseParam(): MoviesInTheaterInputParam? {
        with(moviesContext) {
            var lastMoviePageIndex = 0 // by default, always get the first page
            var lastMoviePage: MoviePage? = null

            if (getAllMoviePages().isNotEmpty()) {
                lastMoviePage = getAllMoviePages().last()
                lastMoviePageIndex = lastMoviePage.page
            }

            val nextPage = lastMoviePageIndex + 1

            if (lastMoviePage != null && nextPage > lastMoviePage.totalPages) {
                playingMoviesView.showEndOfPaging()
                return null
            }

            return MoviesInTheaterInputParam(nextPage, mapper.convertUiGenresToDomainGenres(movieGenres!!))
        }
    }


    /**
     * If the provided [moviePage] is not null, then this method takes care of
     * converting the domain classes to ui classes, set the new page in the context (if possible) and
     * ask to the view to show the new page.
     * If [moviePage] is null, then it asks to the view to show the error.
     */
    private fun processMoviesPage(moviePage: DomainMoviePage?) {
        moviePage?.let {
            val selectedImageConfig = moviesContext.getImageConfigForScreenWidth(getImagesWidthObjective())
            val convertedMoviePage = mapper.convertDomainMoviePageToUiMoviePage(it, selectedImageConfig, moviesContext.movieGenres!!)
            moviesContext.addMoviePage(convertedMoviePage)
            playingMoviesView.showMoviePage(convertedMoviePage)
        } ?: processError()
    }


    /**
     * Process the detected error by showing an internet connection error
     * or a unexpected error message.
     */
    private fun processError() {
        with(interactorDelegate) {
            if (isConnectedToNetwork()) {
                playingMoviesView.showUnexpectedError()
            } else {
                playingMoviesView.showNotConnectedToNetwork()
            }
        }
    }


    /**
     * Finds the proper width for the [ImageConfiguration] to be used by the presenter.
     * The [mapper] will create and set the proper image URL for the movies that are in a [MoviePage].
     * In order to do that, we need to target a given screen width that will be the one that defines
     * the image to retrieve from the server.
     */
    private fun getImagesWidthObjective(): Int {
        return targetScreenWidth ?:
                assignTargetScreenWidth(playingMoviesView.getScreenWidth())
    }


    /**
     * Executes the use case to retrieve the movie page.
     */
    @VisibleForTesting
    fun executeUseCase(param: MoviesInTheaterInputParam) {
        interactorDelegate.executeBackgroundJob(
                {
                    showLoadingIfNeeded()
                    playingMoviesUseCase.execute(param)
                },
                {
                    processMoviesPage(it)
                }
        )
    }

    /**
     * Asks the view to show the initial loading view if it's the initial load.
     */
    private fun showLoadingIfNeeded() {
        with(moviesContext) {
            if (getAllMoviePages().isEmpty()) {
                playingMoviesView.showInitialLoading()
            }
        }
    }

}