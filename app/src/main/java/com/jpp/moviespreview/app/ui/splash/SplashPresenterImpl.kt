package com.jpp.moviespreview.app.ui.splash

import com.jpp.moviespreview.app.domain.MoviesConfiguration
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.interactors.BackgroundInteractor
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractor

/**
 * Presenter for the splash screen.
 *
 * Responsibility: retrieve the movies initial configuration and place it in the UI context.
 *
 * Created by jpp on 10/4/17.
 */
class SplashPresenterImpl(private val useCase: UseCase<Any, MoviesConfiguration>,
                          private val backgroundInteractor: BackgroundInteractor,
                          private val moviesContext: MoviesContext,
                          private val mapper: DomainToUiDataMapper,
                          private val connectivityInteractor: ConnectivityInteractor) : SplashPresenter {

    private lateinit var splashView: SplashView


    override fun linkView(splashView: SplashView) {
        this.splashView = splashView
    }

    override fun retrieveConfig() {
        if (moviesContext.imageConfig == null) {
            backgroundInteractor
                    .executeBackgroundJob({ useCase.execute() },
                            { processMoviesConfig(it) })
        } else {
            splashView.continueToHome()
        }
    }


    private fun processMoviesConfig(moviesConfiguration: MoviesConfiguration?) {
        if (moviesConfiguration != null) {
            moviesContext.imageConfig = mapper.convertConfigurationToImagesConfiguration(moviesConfiguration)
            splashView.continueToHome()
        } else {
            processError(
                    { splashView.showNotConnectedToNetwork() },
                    { splashView.showUnexpectedError() })
        }
    }


    private fun processError(notConnectedToNetwork: () -> Unit?,
                             connectedToNetwork: () -> Unit?) {

        if (connectivityInteractor.isConnectedToNetwork()) {
            connectedToNetwork()
        } else {
            notConnectedToNetwork()
        }
    }
}