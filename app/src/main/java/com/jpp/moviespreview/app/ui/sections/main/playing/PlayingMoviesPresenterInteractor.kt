package com.jpp.moviespreview.app.ui.sections.main.playing

import com.jpp.moviespreview.app.ui.PosterImageConfiguration
import com.jpp.moviespreview.app.ui.UiPage
import com.jpp.moviespreview.app.ui.interactors.ImageConfigurationInteractor
import com.jpp.moviespreview.app.ui.interactors.PaginationInteractor
import com.jpp.moviespreview.app.ui.interactors.PresenterInteractorDelegate

/**
 * Interactor definition for the playing movies presenter.
 *
 * Created by jpp on 12/26/17.
 */
interface PlayingMoviesPresenterInteractor : PresenterInteractorDelegate, PaginationInteractor {

    fun findPosterImageConfigurationForWidth(posterImageConfigs: List<PosterImageConfiguration>,
                                             width: Int): PosterImageConfiguration

}

/**
 * PlayingMoviesPresenterInteractor implementation.
 */
class PlayingMoviesPresenterInteractorImpl(private val presenterInteractorDelegate: PresenterInteractorDelegate,
                                           private val imageConfigInteractor: ImageConfigurationInteractor,
                                           private val paginationInteractorImpl: PaginationInteractor) : PlayingMoviesPresenterInteractor {

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
}