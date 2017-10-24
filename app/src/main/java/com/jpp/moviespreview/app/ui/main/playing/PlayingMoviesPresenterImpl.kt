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
 * //TODO 2 - retrieve movies
 * //TODO 3 - error
 * //TODO 4 - paging
 *
 *
 * Created by jpp on 10/23/17.
 */
class PlayingMoviesPresenterImpl(private val moviesContext: MoviesContext,
                                 private val backgroundInteractor: BackgroundInteractor,
                                 private val playingMoviesUseCase: UseCase<MoviesInTheaterInputParam, MoviePage>,
                                 private val mapper: DomainToUiDataMapper) : PlayingMoviesPresenter {

    private lateinit var playingMoviesView: PlayingMoviesView

    override fun linkView(view: PlayingMoviesView) {
        playingMoviesView = view
        retrievePlayingMoviesIfPossible()
    }


    private fun retrievePlayingMoviesIfPossible() {
        if (moviesContext.isConfigCompleted()) {
            backgroundInteractor.executeBackgroundJob(
                    {
                        val param = createNextUseCaseParam(0, mapper.convertUiGenresToDomainGenres(moviesContext.movieGenres!!))
                        playingMoviesUseCase.execute(param)
                    },
                    { processMoviesPage(it) })

        } else {
            playingMoviesView.backToSplashScreen()
        }
    }


    private fun createNextUseCaseParam(page: Int, genres: List<DomainGenre>) = MoviesInTheaterInputParam(page, genres)


    private fun processMoviesPage(moviePage: MoviePage?) {
        if (moviePage != null) {
            //add to context
            //show
        } else {
            //show error
        }
    }

}