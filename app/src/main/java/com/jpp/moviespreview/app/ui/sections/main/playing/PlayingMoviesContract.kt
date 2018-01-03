package com.jpp.moviespreview.app.ui.sections.main.playing

import com.jpp.moviespreview.app.ui.Movie
import com.jpp.moviespreview.app.ui.MoviePage

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
    fun updateMovie(movie: Movie)
}


interface PlayingMoviesPresenter {
    fun linkView(view: PlayingMoviesView)
    fun getNextMoviePage()
    fun onMovieSelected(movie: Movie)
    fun refreshData()
    fun onMovieImageSelected(movie: Movie, position:Int)
}