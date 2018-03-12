package com.jpp.moviespreview.app.ui.sections.detail.credits

import com.jpp.moviespreview.app.ui.Movie
import com.jpp.moviespreview.app.ui.MoviesContextHandler
import com.jpp.moviespreview.app.ui.ProfileImageConfiguration
import com.jpp.moviespreview.app.ui.interactors.BackgroundExecutor
import com.jpp.moviespreview.app.ui.interactors.ImageConfigurationManager
import com.jpp.moviespreview.app.ui.sections.detail.CreditsData
import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailCreditsInteractor
import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailCreditsPresenter
import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailCreditsView
import com.jpp.moviespreview.app.util.extentions.whenNotNull

/**
 * [MovieDetailCreditsPresenter] implementation.
 *
 * Created by jpp on 12/20/17.
 */
class MovieDetailCreditsPresenterImpl(private val moviesContextHandler: MoviesContextHandler,
                                      private val backgroundExecutor: BackgroundExecutor,
                                      private val interactor: MovieDetailCreditsInteractor,
                                      private val imageConfigManager: ImageConfigurationManager) : MovieDetailCreditsPresenter {

    private lateinit var viewInstance: MovieDetailCreditsView
    private val creditsData: CreditsData by lazy { CreditsData({ observeData() }) }

    override fun linkView(view: MovieDetailCreditsView) {
        whenNotNull(moviesContextHandler.getSelectedMovie(), {
            with(moviesContextHandler) {
                getCreditsForMovie(it)?.let {
                    view.showMovieCredits(it)
                } ?: run {
                    viewInstance = view
                    retrieveMovieCredits(it)
                }
            } // no else -> MovieDetailImagesPresenterImpl handles it
        })
    }


    /**
     * Observes the data that will be updated by the Command.
     */
    private fun observeData() {
        with(creditsData) {
            whenNotNull(credits, {
                backgroundExecutor.executeUiJob { viewInstance.showMovieCredits(it) }
            })

            whenNotNull(error, {
                backgroundExecutor.executeUiJob { viewInstance.showErrorRetrievingCredits() }
            })
        }
    }


    /**
     * Retrieves the movie credits for the provided [movie]
     */
    private fun retrieveMovieCredits(movie: Movie) {
        viewInstance.showLoading()
        backgroundExecutor.executeBackgroundJob { interactor.retrieveMovieCredits(creditsData, movie, getProfileImageConfiguration()) }
    }


    /**
     * Finds the [ProfileImageConfiguration] fir the current view.
     */
    private fun getProfileImageConfiguration(): ProfileImageConfiguration {
        with(moviesContextHandler) {
            getProfileImageConfigs()?.let {
                return imageConfigManager.findProfileImageConfigurationForHeight(it, viewInstance.getTargetProfileImageHeight())
            } ?: run {
                throw IllegalStateException("Movies context should be completed at this point")
            }
        }
    }

}