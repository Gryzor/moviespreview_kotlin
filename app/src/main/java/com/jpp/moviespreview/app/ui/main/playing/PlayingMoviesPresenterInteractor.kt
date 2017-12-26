package com.jpp.moviespreview.app.ui.main.playing

import com.jpp.moviespreview.app.ui.PosterImageConfiguration
import com.jpp.moviespreview.app.ui.interactors.PresenterInteractorDelegate
import com.jpp.moviespreview.app.util.extentions.transformToInt

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
class PlayingMoviesPresenterInteractorImpl(private val presenterInteractorDelegate: PresenterInteractorDelegate) : PlayingMoviesPresenterInteractor {

    private var selectedPosterImageConfig: PosterImageConfiguration? = null


    override fun isConnectedToNetwork() = presenterInteractorDelegate.isConnectedToNetwork()

    override fun <T> executeBackgroundJob(backgroundJob: () -> T?, uiJob: (T?) -> Unit?) {
        presenterInteractorDelegate.executeBackgroundJob(backgroundJob, uiJob)
    }

    override fun isIdle() = presenterInteractorDelegate.isIdle()


    /**
     * Finds a suitable [PosterImageConfiguration] for the provided [width].
     * If none is appropriate, the last [PosterImageConfiguration] in the [posterImageConfigs] is
     * returned.
     */
    override fun findPosterImageConfigurationForWidth(posterImageConfigs: List<PosterImageConfiguration>, width: Int): PosterImageConfiguration {
        if (selectedPosterImageConfig == null) {
            selectedPosterImageConfig = posterImageConfigs.firstOrNull {
                isImageConfigForWidth(width, it)
            } ?: posterImageConfigs.last()
        }
        return selectedPosterImageConfig!!
    }


    private fun isImageConfigForWidth(width: Int, posterImageConfig: PosterImageConfiguration): Boolean {
        with(posterImageConfig) {
            val realSize = size.transformToInt()
            return realSize != null && realSize > width
        }
    }
}