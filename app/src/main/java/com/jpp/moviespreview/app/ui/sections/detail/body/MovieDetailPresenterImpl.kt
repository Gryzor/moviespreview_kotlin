package com.jpp.moviespreview.app.ui.sections.detail.body

import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailPresenter
import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailView

/**
 * Presenter implementation for the movies details section
 *
 *
 * Created by jpp on 11/4/17.
 */
class MovieDetailPresenterImpl(private val moviesContext: MoviesContext) : MovieDetailPresenter {


    private lateinit var view: MovieDetailView

    override fun linkView(movieDetailView: MovieDetailView) {
        view = movieDetailView
        moviesContext.selectedMovie!!.let {
            view.showMovieOverview(it.overview)
            view.showMovieGenres(it.genres)
            view.showMovieVoteCount(it.voteCount)
            view.showMoviePopularity(it.popularity)
        }
    }
}