package com.jpp.moviespreview.app.ui.detail

import com.jpp.moviespreview.app.ui.Movie

/**
 * Created by jpp on 11/4/17.
 */


interface MovieDetailView {
    fun showMovieNotSelected()
    fun showMovie(movie: Movie)
}

interface MovieDetailPresenter {
    fun linkView(movieDetailView: MovieDetailView)
    fun onMovieImageSelected(position: Int)
}

interface MovieDetailImagesView {
    fun showMovieImages(imagesUrl: List<String>, selectedPosition: Int)
    fun showMovieTitle(movieTitle: String)
    fun showMovieNotSelected()
}

interface MovieDetailImagesPresenter {
    fun linkView(movieDetailView: MovieDetailImagesView)
    fun onMovieImageSelected(position: Int)
}