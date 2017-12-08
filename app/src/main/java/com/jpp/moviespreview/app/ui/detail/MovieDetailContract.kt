package com.jpp.moviespreview.app.ui.detail

/**
 * Created by jpp on 11/4/17.
 */


interface MovieDetailView {
    fun showMovieImages(imagesUrl: List<String>, selectedPosition: Int)
    fun showMovieNotSelected()
}

interface MovieDetailPresenter {
    fun linkView(movieDetailView: MovieDetailView)
    fun onMovieImageSelected(position: Int)
}