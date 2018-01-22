package com.jpp.moviespreview.app.ui.sections.search

import com.jpp.moviespreview.app.ui.PosterImageConfiguration
import com.jpp.moviespreview.app.ui.ProfileImageConfiguration
import com.jpp.moviespreview.app.ui.UiPage
import com.jpp.moviespreview.app.ui.interactors.ImageConfigurationInteractor
import com.jpp.moviespreview.app.ui.interactors.PaginationInteractor
import com.jpp.moviespreview.app.ui.interactors.PresenterInteractorDelegate

/**
 * PlayingMoviesPresenterInteractor implementation.
 */
class MultiSearchPresenterInteractorImpl(private val presenterInteractorDelegate: PresenterInteractorDelegate,
                                         private val imageConfigInteractor: ImageConfigurationInteractor,
                                         private val paginationInteractorImpl: PaginationInteractor) : MultiSearchPresenterInteractor {

    override fun managePagination(getAllPages: () -> List<UiPage>, onEndOfPaging: () -> Unit, onNextPage: (Int) -> Unit) {
        paginationInteractorImpl.managePagination(getAllPages, onEndOfPaging, onNextPage)
    }

    override fun isConnectedToNetwork() = presenterInteractorDelegate.isConnectedToNetwork()

    override fun <T> executeBackgroundJob(backgroundJob: () -> T?, uiJob: (T?) -> Unit?) {
        presenterInteractorDelegate.executeBackgroundJob(backgroundJob, uiJob)
    }

    override fun isIdle() = presenterInteractorDelegate.isIdle()

    override fun findPosterImageConfigurationForWidth(posterImageConfigs: List<PosterImageConfiguration>, width: Int): PosterImageConfiguration =
            imageConfigInteractor.findPosterImageConfigurationForWidth(posterImageConfigs, width)

    override fun findProfileImageConfigurationForHeight(profileImageConfigs: List<ProfileImageConfiguration>, height: Int): ProfileImageConfiguration =
            imageConfigInteractor.findProfileImageConfigurationForHeight(profileImageConfigs, height)
}