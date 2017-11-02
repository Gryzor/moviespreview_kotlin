package com.jpp.moviespreview.app.ui.main.playing

import android.annotation.SuppressLint
import android.support.annotation.VisibleForTesting
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
 * Created by jpp on 10/23/17.
 */
class PlayingMoviesPresenterImpl(private val moviesContext: MoviesContext,
                                 private val interactorDelegate: PlayingMoviesInteractorDelegate,
                                 private val playingMoviesUseCase: UseCase<MoviesInTheaterInputParam, DomainMoviePage>,
                                 private val mapper: DomainToUiDataMapper) : PlayingMoviesPresenter {


    private lateinit var playingMoviesView: PlayingMoviesView
    private var targetScreenWidth: Int? = null

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
            getNextMoviePage()
        }
    }


    @SuppressLint("VisibleForTests")
    override fun getNextMoviePage() {
        if (moviesContext.isConfigCompleted()) {
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


    /**
     * Creates the [MoviesInTheaterInputParam] that is going to be used for the next
     * use case execution. If the scrolling has reached the last possible position,
     * it asks the view to show the end of page and returns null.
     */
    @VisibleForTesting
    fun createNextUseCaseParam(): MoviesInTheaterInputParam? {
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


        return MoviesInTheaterInputParam(nextPage, mapper.convertUiGenresToDomainGenres(genres))
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
        if (moviesContext.getAllMoviePages().isEmpty()) {
            playingMoviesView.showInitialLoading()
        }
    }

}