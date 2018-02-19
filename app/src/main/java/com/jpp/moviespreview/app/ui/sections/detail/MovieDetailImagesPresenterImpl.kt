package com.jpp.moviespreview.app.ui.sections.detail

import com.jpp.moviespreview.app.ui.ApplicationMoviesContext

/**
 * Presenter to control the images section of the Movies detail view.
 */
class MovieDetailImagesPresenterImpl(private val moviesContext: ApplicationMoviesContext)
    : MovieDetailImagesPresenter {

    private lateinit var view: MovieDetailImagesView


    override fun linkView(movieDetailView: MovieDetailImagesView) {
        view = movieDetailView

        moviesContext.selectedMovie?.let {
            view.showMovieImage(it.getPosterPath())
            view.showMovieTitle(it.title)
        } ?: view.showMovieNotSelected()
    }
}