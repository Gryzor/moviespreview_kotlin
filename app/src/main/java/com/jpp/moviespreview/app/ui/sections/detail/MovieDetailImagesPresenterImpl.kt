package com.jpp.moviespreview.app.ui.sections.detail

import com.jpp.moviespreview.app.ui.MoviesContextHandler

/**
 * Presenter to control the images section of the Movies detail view.
 */
class MovieDetailImagesPresenterImpl(private val moviesContextHandler: MoviesContextHandler)
    : MovieDetailImagesPresenter {

    private lateinit var view: MovieDetailImagesView


    override fun linkView(movieDetailView: MovieDetailImagesView) {
        view = movieDetailView

        moviesContextHandler.getSelectedMovie()?.let {
            view.showMovieImage(it.getPosterPath())
            view.showMovieTitle(it.title)
        } ?: view.showMovieNotSelected()
    }
}