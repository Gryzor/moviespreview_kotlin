package com.jpp.moviespreview.app.ui.main.playing

import com.jpp.moviespreview.app.ui.MoviePage

/**
 * Contains the MVP contract for the movies in theater (currently playing) section
 *
 * Created by jpp on 10/23/17.
 */

interface PlayingMoviesView {

    fun backToSplashScreen()
    fun showMoviePage(moviePage: MoviePage)
}


interface PlayingMoviesPresenter {

    fun linkView(view: PlayingMoviesView)


}