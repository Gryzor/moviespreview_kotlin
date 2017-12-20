package com.jpp.moviespreview.app.ui.detail

import com.jpp.moviespreview.app.ui.MoviesContext

/**
 * Presenter to control the images section of the Movies detail view.
 */
class MovieDetailImagesPresenterImpl(private val moviesContext: MoviesContext)
    : MovieDetailImagesPresenter {

    private lateinit var view: MovieDetailImagesView

    override fun onMovieImageSelected(position: Int) {
        moviesContext.selectedMovie!!.currentImageShown = position
    }

    override fun linkView(movieDetailImagesView: MovieDetailImagesView) {
        view = movieDetailImagesView

        moviesContext.selectedMovie?.let {
            view.showMovieImages(it.images, it.currentImageShown)
            view.showMovieTitle(it.title)
        } ?: view.showMovieNotSelected()
    }
}