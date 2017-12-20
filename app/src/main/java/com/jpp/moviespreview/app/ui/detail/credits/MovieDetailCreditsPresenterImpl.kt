package com.jpp.moviespreview.app.ui.detail.credits

import com.jpp.moviespreview.app.domain.Movie
import com.jpp.moviespreview.app.domain.MovieCredits
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.detail.MovieDetailCreditsPresenter
import com.jpp.moviespreview.app.ui.detail.MovieDetailCreditsView
import com.jpp.moviespreview.app.ui.interactors.PresenterInteractorDelegate

/**
 * Created by jpp on 12/20/17.
 */
class MovieDetailCreditsPresenterImpl(private val moviesContext: MoviesContext,
                                      private val interactorDelegate: PresenterInteractorDelegate,
                                      private val useCase: UseCase<Movie, MovieCredits>,
                                      private val mapper: DomainToUiDataMapper) : MovieDetailCreditsPresenter {


    override fun linkView(view: MovieDetailCreditsView) {
        with(view) {
            view.showLoading()
            interactorDelegate.executeBackgroundJob(
                    { useCase.execute(mapper.convertUiMovieIntoDomainMovie(moviesContext.selectedMovie!!)) },
                    {
                        if (it != null) {
                            view.showMovieCredits(mapper.convertDomainCreditsInUiCredits(it.cast, it.crew))
                        } else {
                            view.showErrorRetrievingCredits()
                        }
                    }
            )
        }
    }
}