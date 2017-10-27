package com.jpp.moviespreview.app.ui.main.playing

import com.jpp.moviespreview.app.domain.MoviePage
import com.jpp.moviespreview.app.domain.MoviesInTheaterInputParam
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.interactors.BackgroundInteractor
import com.jpp.moviespreview.app.domain.Genre as DomainGenre

/**
 * Presenter implementation for the playing movies in theater section
 *
 * //TODO 1 - verify if context is ready and go back to splash if not
 * //TODO 3 - error
 * //TODO 4 - paging
 *
 * Created by jpp on 10/23/17.
 */
class PlayingMoviesPresenterImpl(private val moviesContext: MoviesContext,
                                 private val backgroundInteractor: BackgroundInteractor,
                                 private val playingMoviesUseCase: UseCase<MoviesInTheaterInputParam, MoviePage>,
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
            retrievePlayingMoviesIfPossible()
        }
    }


    private fun retrievePlayingMoviesIfPossible() {
        if (moviesContext.isConfigCompleted()) {
            backgroundInteractor.executeBackgroundJob(
                    {
                        val param = createNextUseCaseParam(1, mapper.convertUiGenresToDomainGenres(moviesContext.movieGenres!!))
                        playingMoviesUseCase.execute(param)
                    },
                    { processMoviesPage(it) })

        } else {
            playingMoviesView.backToSplashScreen()
        }
    }


    private fun createNextUseCaseParam(page: Int, genres: List<DomainGenre>) = MoviesInTheaterInputParam(page, genres)


    /**
     * If the provided [moviePage] is not null, then this method takes care of
     * converting the domain classes to ui classes, set the new page in the context (if possible) and
     * ask to the view to show the new page.
     * If [moviePage] is null, then it asks to the view to show the error.
     */
    private fun processMoviesPage(moviePage: MoviePage?) {
        if (moviePage != null) {
            val selectedImageConfig = moviesContext.getImageConfigForScreenWidth(getImagesWidthObjective())
            val convertedMoviePage = mapper.convertDomainMoviePageToUiMoviePage(moviePage, selectedImageConfig, moviesContext.movieGenres!!)
            moviesContext.addMoviePage(convertedMoviePage)
            playingMoviesView.showMoviePage(convertedMoviePage)
        } else {
            //TODO JPP show error
        }
    }


    private fun getImagesWidthObjective(): Int {
        if (targetScreenWidth == null) {
            targetScreenWidth = playingMoviesView.getScreenWidth()
        }
        return targetScreenWidth!!
    }

}