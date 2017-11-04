package com.jpp.moviespreview.app.ui.splash

import com.jpp.moviespreview.app.domain.Genre
import com.jpp.moviespreview.app.domain.MoviesConfiguration
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.interactors.BackgroundInteractor
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractor

/**
 * Presenter for the splash screen.
 *
 * Responsibility:
 *  1 - retrieve the movies initial configuration and place it in the UI context.
 *  2 - retrieve the movies genres and place it in the UI context.
 *
 * Created by jpp on 10/4/17.
 */
class SplashPresenterImpl(private val moviesContext: MoviesContext,
                          private val backgroundInteractor: BackgroundInteractor,
                          private val mapper: DomainToUiDataMapper,
                          private val connectivityInteractor: ConnectivityInteractor,
                          private val moviesConfigurationUseCase: UseCase<Any, MoviesConfiguration>,
                          private val moviesGenresUseCase: UseCase<Any, List<Genre>> ) : SplashPresenter {

    private lateinit var splashView: SplashView


    override fun linkView(splashView: SplashView) {
        this.splashView = splashView

        if (moviesContext.isConfigCompleted()) {
            splashView.continueToHome()
        } else {
            retrieveConfig()
        }
    }

    private fun retrieveConfig() {
        backgroundInteractor
                .executeBackgroundJob({ moviesConfigurationUseCase.execute() },
                        { processMoviesConfig(it) })

        backgroundInteractor
                .executeBackgroundJob({ moviesGenresUseCase.execute() },
                        { processGenresConfig(it) })

    }

    private fun processMoviesConfig(moviesConfiguration: MoviesConfiguration?) {
        if (moviesConfiguration != null) {
            moviesContext.imageConfig = mapper.convertConfigurationToImagesConfiguration(moviesConfiguration)
            continueToHomeIfConfigReady()
        } else {
            processError()
        }
    }

    private fun processGenresConfig(genres: List<Genre>?) {
        if (genres != null) {
            moviesContext.movieGenres = mapper.convertDomainGenresIntoUiGenres(genres)
            continueToHomeIfConfigReady()
        } else {
            processError()
        }
    }


    private fun continueToHomeIfConfigReady() {
        if (moviesContext.isConfigCompleted()) {
            splashView.continueToHome()
        }
    }


    private fun processError() {
        if (connectivityInteractor.isConnectedToNetwork()) {
            splashView.showUnexpectedError()
        } else {
            splashView.showNotConnectedToNetwork()
        }
    }
}