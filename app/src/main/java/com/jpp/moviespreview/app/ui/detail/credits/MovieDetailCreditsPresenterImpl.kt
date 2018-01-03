package com.jpp.moviespreview.app.ui.detail.credits

import com.jpp.moviespreview.app.domain.Movie
import com.jpp.moviespreview.app.domain.MovieCredits
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.ProfileImageConfiguration
import com.jpp.moviespreview.app.ui.detail.MovieDetailCreditsPresenter
import com.jpp.moviespreview.app.ui.detail.MovieDetailCreditsView

/**
 * Created by jpp on 12/20/17.
 */
class MovieDetailCreditsPresenterImpl(private val moviesContext: MoviesContext,
                                      private val interactorDelegate: MovieDetailsCreditsPresenterInteractor,
                                      private val useCase: UseCase<Movie, MovieCredits>,
                                      private val mapper: DomainToUiDataMapper) : MovieDetailCreditsPresenter {

    private var selectedProfileImageConfig: ProfileImageConfiguration? = null
    private lateinit var viewInstance: MovieDetailCreditsView

    override fun linkView(view: MovieDetailCreditsView) {
        viewInstance = view
        with(view) {
            val creditsInContext = moviesContext.getCreditsForMovie(moviesContext.selectedMovie!!)
            if (creditsInContext != null) {
                showMovieCredits(creditsInContext)
            } else {
                retrieveMovieCredits()
            }
        }
    }

    private fun retrieveMovieCredits() {
        with(viewInstance) {
            showLoading()
            interactorDelegate.executeBackgroundJob(
                    { useCase.execute(mapper.convertUiMovieIntoDomainMovie(moviesContext.selectedMovie!!)) },
                    {
                        if (it != null) {
                            val uiCredits = mapper.convertDomainCreditsInUiCredits(it.cast.sortedBy { it.order }, it.crew, getProfileImageConfiguration())
                            moviesContext.putCreditsForMovie(moviesContext.selectedMovie!!, uiCredits)
                            showMovieCredits(uiCredits)
                        } else {
                            showErrorRetrievingCredits()
                        }
                    }
            )
        }
    }


    private fun getProfileImageConfiguration(): ProfileImageConfiguration {
        if (selectedProfileImageConfig == null) {
            selectedProfileImageConfig = interactorDelegate.findProfileImageConfigurationForHeight(moviesContext.profileImageConfig!!, viewInstance.getTargetProfileImageHeight())
        }
        return selectedProfileImageConfig!!
    }
}