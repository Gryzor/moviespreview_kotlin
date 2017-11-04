package com.jpp.moviespreview.app.ui.detail

import com.jpp.moviespreview.app.ui.Movie

/**
 * Created by jpp on 11/4/17.
 */


interface MovieDetailView {
    fun showMovie(movie: Movie)
}

interface MovieDetailPresenter {
    fun linkView(movieDetailView: MovieDetailView)
}