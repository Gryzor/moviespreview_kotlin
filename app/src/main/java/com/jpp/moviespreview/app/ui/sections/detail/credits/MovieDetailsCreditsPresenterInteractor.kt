package com.jpp.moviespreview.app.ui.sections.detail.credits

import com.jpp.moviespreview.app.ui.ProfileImageConfiguration
import com.jpp.moviespreview.app.ui.interactors.ImageConfigurationInteractor
import com.jpp.moviespreview.app.ui.interactors.PresenterInteractorDelegate

/**
 * Interactor definition for the movies credits presenter.
 *
 * Created by jpp on 12/26/17.
 */
interface MovieDetailsCreditsPresenterInteractor : PresenterInteractorDelegate {

    fun findProfileImageConfigurationForHeight(profileImageConfigs: List<ProfileImageConfiguration>,
                                               height: Int): ProfileImageConfiguration

}

class MovieDetailsCreditsPresenterInteractorImpl(private val presenterInteractorDelegate: PresenterInteractorDelegate,
                                                 private val imageConfigInteractor: ImageConfigurationInteractor)
    : MovieDetailsCreditsPresenterInteractor {

    override fun isConnectedToNetwork() = presenterInteractorDelegate.isConnectedToNetwork()

    override fun <T> executeBackgroundJob(backgroundJob: () -> T?, uiJob: (T?) -> Unit?) {
        presenterInteractorDelegate.executeBackgroundJob(backgroundJob, uiJob)
    }

    override fun isIdle() = presenterInteractorDelegate.isIdle()


    override fun findProfileImageConfigurationForHeight(profileImageConfigs: List<ProfileImageConfiguration>, height: Int)
            = imageConfigInteractor.findProfileImageConfigurationForHeight(profileImageConfigs, height)
}
