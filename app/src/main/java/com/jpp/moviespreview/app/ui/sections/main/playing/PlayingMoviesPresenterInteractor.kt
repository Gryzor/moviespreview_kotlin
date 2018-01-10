package com.jpp.moviespreview.app.ui.sections.main.playing

import com.jpp.moviespreview.app.ui.PosterImageConfiguration
import com.jpp.moviespreview.app.ui.interactors.ImageConfigurationPresenterDelegate
import com.jpp.moviespreview.app.ui.interactors.PresenterInteractorDelegate

/**
 * Interactor definition for the playing movies presenter.
 *
 * Created by jpp on 12/26/17.
 */
interface PlayingMoviesPresenterInteractor : PresenterInteractorDelegate {

    fun findPosterImageConfigurationForWidth(posterImageConfigs: List<PosterImageConfiguration>,
                                             width: Int): PosterImageConfiguration

}

/**
 * PlayingMoviesPresenterInteractor implementation.
 */
class PlayingMoviesPresenterInteractorImpl(private val presenterInteractorDelegate: PresenterInteractorDelegate,
                                           private val imageConfigPresenterDelegate: ImageConfigurationPresenterDelegate) : PlayingMoviesPresenterInteractor {

    override fun isConnectedToNetwork() = presenterInteractorDelegate.isConnectedToNetwork()

    override fun <T> executeBackgroundJob(backgroundJob: () -> T?, uiJob: (T?) -> Unit?) {
        presenterInteractorDelegate.executeBackgroundJob(backgroundJob, uiJob)
    }

    override fun isIdle() = presenterInteractorDelegate.isIdle()

    override fun findPosterImageConfigurationForWidth(posterImageConfigs: List<PosterImageConfiguration>, width: Int): PosterImageConfiguration =
            imageConfigPresenterDelegate.findPosterImageConfigurationForWidth(posterImageConfigs, width)
}