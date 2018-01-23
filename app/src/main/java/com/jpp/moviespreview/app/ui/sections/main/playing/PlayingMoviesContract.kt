package com.jpp.moviespreview.app.ui.sections.main.playing

import com.jpp.moviespreview.app.ui.Movie
import com.jpp.moviespreview.app.ui.MoviePage
import com.jpp.moviespreview.app.ui.PosterImageConfiguration
import com.jpp.moviespreview.app.ui.interactors.PaginationInteractor
import com.jpp.moviespreview.app.ui.interactors.PresenterInteractorDelegate

/**
 * Contains the MVP contract for the movies in theater (currently playing) section
 *
 * Created by jpp on 10/23/17.
 */

interface PlayingMoviesView {
    fun backToSplashScreen()
    fun showMoviePage(moviePage: MoviePage)
    fun getScreenWidth(): Int
    fun showUnexpectedError()
    fun showNotConnectedToNetwork()
    fun showEndOfPaging()
    fun showInitialLoading()
}


interface PlayingMoviesPresenter {
    fun linkView(view: PlayingMoviesView)
    fun getNextMoviePage()
    fun onMovieSelected(movie: Movie)
}


/**
 * Interactor definition for the playing movies presenter.
 */
interface PlayingMoviesPresenterInteractor : PresenterInteractorDelegate, PaginationInteractor {

    fun findPosterImageConfigurationForWidth(posterImageConfigs: List<PosterImageConfiguration>,
                                             width: Int): PosterImageConfiguration

}