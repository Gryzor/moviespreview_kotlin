package com.jpp.moviespreview.app.ui.sections.detail

import com.jpp.moviespreview.app.ui.MoviesContextHandler

/**
 * Presenter to control the images section of the Movies detail view.
 */
class MovieDetailImagesPresenterImpl(private val moviesContextHandler: MoviesContextHandler)
    : MovieDetailImagesPresenter {

    override fun linkView(movieDetailView: MovieDetailImagesView) {
        with(movieDetailView) {
            moviesContextHandler.getSelectedMovie()?.let {
                showMovieImage(it.getPosterPath())
                showMovieTitle(it.title)
            } ?: showMovieNotSelected()
        }
    }
}