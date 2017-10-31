package com.jpp.moviespreview.app.ui.main.playing

import com.jpp.moviespreview.app.domain.MoviesInTheaterInputParam
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.ImageConfiguration
import com.jpp.moviespreview.app.ui.MoviePage
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.domain.Genre as DomainGenre
import com.jpp.moviespreview.app.domain.MoviePage as DomainMoviePage

/**
 * Presenter implementation for the playing movies in theater section
 *
 * //TODO clear DB when close ?
 * //TODO loading
 *
 * Created by jpp on 10/23/17.
 */
class PlayingMoviesPresenterImpl(private val moviesContext: MoviesContext,
                                 private val interactorDelegate: PlayingMoviesInteractorDelegate,
                                 private val playingMoviesUseCase: UseCase<MoviesInTheaterInputParam, DomainMoviePage>,
                                 private val mapper: DomainToUiDataMapper) : PlayingMoviesPresenter {


    private lateinit var playingMoviesView: PlayingMoviesView
    private var targetScreenWidth: Int? = null
    private var canRetrieveMovies = true

    override fun linkView(view: PlayingMoviesView) {
        playingMoviesView = view
        loadOrRetrieveMovies()
    }


    /**
     * Verifies if there are pages loaded into the context. If there are, loads those
     * pages into the screen. If not, it attempts to retrieve the new pages.
     */
    private fun loadOrRetrieveMovies() {
        if (moviesContext.hasMoviePages()) {
            for (page in moviesContext.getAllMoviePages()) {
                playingMoviesView.showMoviePage(page)
            }
        } else {
            retrievePlayingMoviesIfPossible()
        }
    }


    /**
     * Retrieves the list of movies if the [moviesContext] is completed (the initial configuration
     * is completed). If not, takes the flow back to the splash screen in order to refresh the
     * initial configuration.
     */
    private fun retrievePlayingMoviesIfPossible() {
        getNextMoviePage()
    }


    /**
     * Creates the [MoviesInTheaterInputParam] that is going to be used for the next
     * use case execution. If the scrolling has reached the last possible position,
     * it asks the view to show the end of page and returns null.
     */
    private fun createNextUseCaseParam(): MoviesInTheaterInputParam? {
        var lastMoviePageIndex = 0 // by default, always get the first page
        var lastMoviePage: MoviePage? = null

        if (!moviesContext.getAllMoviePages().isEmpty()) {
            lastMoviePage = moviesContext.getAllMoviePages().last()
            lastMoviePageIndex = lastMoviePage.page
        }

        val genres = moviesContext.movieGenres

        if (genres == null) {
            playingMoviesView.showUnexpectedError()
            return null
        }

        val nextPage = lastMoviePageIndex + 1

        if (lastMoviePage != null && nextPage > lastMoviePage.totalPages) {
            playingMoviesView.showEndOfPaging()
            return null
        }


        return MoviesInTheaterInputParam(nextPage, mapper.convertUiGenresToDomainGenres(moviesContext.movieGenres!!))
    }


    /**
     * If the provided [moviePage] is not null, then this method takes care of
     * converting the domain classes to ui classes, set the new page in the context (if possible) and
     * ask to the view to show the new page.
     * If [moviePage] is null, then it asks to the view to show the error.
     */
    private fun processMoviesPage(moviePage: DomainMoviePage?) {
        if (moviePage != null) {
            val selectedImageConfig = moviesContext.getImageConfigForScreenWidth(getImagesWidthObjective())
            val convertedMoviePage = mapper.convertDomainMoviePageToUiMoviePage(moviePage, selectedImageConfig, moviesContext.movieGenres!!)
            moviesContext.addMoviePage(convertedMoviePage)
            playingMoviesView.showMoviePage(convertedMoviePage)
        } else {
            processError()
        }
    }


    /**
     * Process the detected error by showing an internet connection error
     * or a unexpected error message.
     */
    private fun processError() {
        if (interactorDelegate.isConnectedToNetwork()) {
            playingMoviesView.showUnexpectedError()
        } else {
            playingMoviesView.showNotConnectedToNetwork()
        }
    }


    /**
     * Finds the proper width for the [ImageConfiguration] to be used by the presenter.
     */
    private fun getImagesWidthObjective(): Int {
        if (targetScreenWidth == null) {
            targetScreenWidth = playingMoviesView.getScreenWidth()
        }
        return targetScreenWidth!!
    }


    override fun getNextMoviePage() {
        if (moviesContext.isConfigCompleted()) {
            if (canRetrieveMovies) {
                val param = createNextUseCaseParam()
                if (param != null) {
                    executeUseCase(param)
                }
            }
        } else {
            playingMoviesView.backToSplashScreen()
        }
    }

    private fun executeUseCase(param: MoviesInTheaterInputParam) {
        canRetrieveMovies = false
        interactorDelegate.executeBackgroundJob(
                {
                    showLoadingIfNeeded()
                    playingMoviesUseCase.execute(param)
                },
                {
                    canRetrieveMovies = true
                    processMoviesPage(it)
                }
        )
    }

    /**
     * Asks the view to show the initial loading view if it's the initial load.
     */
    private fun showLoadingIfNeeded() {
        if (moviesContext.getAllMoviePages().isEmpty()) {
            playingMoviesView.showInitialLoading()
        }
    }

}