package com.jpp.moviespreview.app.ui.main.playing

/**
 * Presenter implememtation for the playing movies in theater section
 *
 * Created by jpp on 10/23/17.
 */
class PlayingMoviesPresenterImpl : PlayingMoviesPresenter {

    private lateinit var playingMoviesView: PlayingMoviesView

    override fun linkView(view: PlayingMoviesView) {
        playingMoviesView = view
    }

}