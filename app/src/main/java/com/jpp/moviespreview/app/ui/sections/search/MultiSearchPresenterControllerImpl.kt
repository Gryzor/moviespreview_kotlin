package com.jpp.moviespreview.app.ui.sections.search

import com.jpp.moviespreview.app.ui.PosterImageConfiguration
import com.jpp.moviespreview.app.ui.ProfileImageConfiguration
import com.jpp.moviespreview.app.ui.UiPage
import com.jpp.moviespreview.app.ui.interactors.ImageConfigurationManager
import com.jpp.moviespreview.app.ui.interactors.PaginationController
import com.jpp.moviespreview.app.ui.interactors.PresenterInteractorDelegate

/**
 * PlayingMoviesPresenterController implementation.
 */
class MultiSearchPresenterControllerImpl(private val presenterInteractorDelegate: PresenterInteractorDelegate,
                                         private val imageConfigManager: ImageConfigurationManager,
                                         private val paginationControllerImpl: PaginationController) : MultiSearchPresenterController {

    override fun controlPagination(getAllPages: () -> List<UiPage>, onEndOfPaging: () -> Unit, onNextPage: (Int) -> Unit) {
        paginationControllerImpl.controlPagination(getAllPages, onEndOfPaging, onNextPage)
    }

    override fun isConnectedToNetwork() = presenterInteractorDelegate.isConnectedToNetwork()

    override fun <T> executeBackgroundJob(backgroundJob: () -> T?, uiJob: (T?) -> Unit?) {
        presenterInteractorDelegate.executeBackgroundJob(backgroundJob, uiJob)
    }

    override fun isIdle() = presenterInteractorDelegate.isIdle()

    override fun findPosterImageConfigurationForWidth(posterImageConfigs: List<PosterImageConfiguration>, width: Int): PosterImageConfiguration =
            imageConfigManager.findPosterImageConfigurationForWidth(posterImageConfigs, width)

    override fun findProfileImageConfigurationForHeight(profileImageConfigs: List<ProfileImageConfiguration>, height: Int): ProfileImageConfiguration =
            imageConfigManager.findProfileImageConfigurationForHeight(profileImageConfigs, height)
}