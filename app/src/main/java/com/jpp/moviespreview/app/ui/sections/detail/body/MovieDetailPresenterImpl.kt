package com.jpp.moviespreview.app.ui.sections.detail.body

import com.jpp.moviespreview.app.ui.MoviesContextHandler
import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailPresenter
import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailView
import com.jpp.moviespreview.app.util.extentions.whenNotNull

/**
 * Presenter implementation for the movies details section
 *
 *
 * Created by jpp on 11/4/17.
 */
class MovieDetailPresenterImpl(private val moviesContextHandler: MoviesContextHandler) : MovieDetailPresenter {


    override fun linkView(movieDetailView: MovieDetailView) {
        with(movieDetailView) {
            whenNotNull(moviesContextHandler.getSelectedMovie(), {
                showMovieOverview(it.overview)
                showMovieGenres(it.genres)
                showMovieVoteCount(it.voteCount)
                showMoviePopularity(it.popularity)
            }) // no else -> MovieDetailImagesPresenterImpl handles it
        }
    }
}