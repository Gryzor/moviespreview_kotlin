package com.jpp.moviespreview.app.ui.detail

import com.jpp.moviespreview.app.domain.Movie
import com.jpp.moviespreview.app.domain.MovieCredits
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.interactors.PresenterInteractorDelegate

/**
 * Presenter implementation for the movies details section
 *
 *
 * Created by jpp on 11/4/17.
 */
class MovieDetailPresenterImpl(private val moviesContext: MoviesContext,
                               private val interactorDelegate: PresenterInteractorDelegate,
                               private val mapper: DomainToUiDataMapper,
                               private val usecase: UseCase<Movie, MovieCredits>) : MovieDetailPresenter {


    private lateinit var view: MovieDetailView

    override fun linkView(movieDetailView: MovieDetailView) {
        view = movieDetailView
        moviesContext.selectedMovie?.let {
            view.showMovieImages(it.backdropPath)
        } ?: view.showMovieNotSelected()

    }


}