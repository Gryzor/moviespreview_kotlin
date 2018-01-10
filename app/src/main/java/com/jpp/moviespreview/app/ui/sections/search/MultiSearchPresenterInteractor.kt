package com.jpp.moviespreview.app.ui.sections.search

import com.jpp.moviespreview.app.ui.PosterImageConfiguration
import com.jpp.moviespreview.app.ui.ProfileImageConfiguration
import com.jpp.moviespreview.app.ui.interactors.ImageConfigurationPresenterDelegate
import com.jpp.moviespreview.app.ui.interactors.PresenterInteractorDelegate

/**
 * Created by jpp on 1/9/18.
 */
interface MultiSearchPresenterInteractor : PresenterInteractorDelegate {

    fun findProfileImageConfigurationForHeight(profileImageConfigs: List<ProfileImageConfiguration>,
                                               height: Int): ProfileImageConfiguration

    fun findPosterImageConfigurationForWidth(posterImageConfigs: List<PosterImageConfiguration>,
                                             width: Int): PosterImageConfiguration
}


/**
 * PlayingMoviesPresenterInteractor implementation.
 */
class MultiSearchPresenterInteractorImpl(private val presenterInteractorDelegate: PresenterInteractorDelegate,
                                         private val imageConfigPresenterDelegate: ImageConfigurationPresenterDelegate) : MultiSearchPresenterInteractor {


    override fun isConnectedToNetwork() = presenterInteractorDelegate.isConnectedToNetwork()

    override fun <T> executeBackgroundJob(backgroundJob: () -> T?, uiJob: (T?) -> Unit?) {
        presenterInteractorDelegate.executeBackgroundJob(backgroundJob, uiJob)
    }

    override fun isIdle() = presenterInteractorDelegate.isIdle()

    override fun findPosterImageConfigurationForWidth(posterImageConfigs: List<PosterImageConfiguration>, width: Int): PosterImageConfiguration =
            imageConfigPresenterDelegate.findPosterImageConfigurationForWidth(posterImageConfigs, width)

    override fun findProfileImageConfigurationForHeight(profileImageConfigs: List<ProfileImageConfiguration>, height: Int): ProfileImageConfiguration =
            imageConfigPresenterDelegate.findProfileImageConfigurationForHeight(profileImageConfigs, height)
}