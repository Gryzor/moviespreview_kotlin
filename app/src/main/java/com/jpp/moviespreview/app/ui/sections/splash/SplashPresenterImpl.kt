package com.jpp.moviespreview.app.ui.sections.splash

import com.jpp.moviespreview.app.ui.Error
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.interactors.BackgroundExecutor
import com.jpp.moviespreview.app.util.extentions.whenNotNull
import com.jpp.moviespreview.app.util.extentions.whenNull

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
                          private val backgroundExecutor: BackgroundExecutor,
                          private val interactor: SplashPresenterInteractor) : SplashPresenter {

    private lateinit var splashViewInstance: SplashView
    private val splashData by lazy {
        SplashData({ observeData() })
    }


    override fun linkView(splashView: SplashView) {
        with(moviesContext) {
            if (isConfigCompleted()) {
                splashView.continueToHome()
            } else {
                splashViewInstance = splashView
                backgroundExecutor.executeBackgroundJob { interactor.retrieveConfiguration(splashData) }
            }
        }
    }

    private fun observeData() {
        with(splashData) {
            whenNotNull(posterConfig, { posterConfig -> whenNull((moviesContext.posterImageConfig), { moviesContext.posterImageConfig = posterConfig }) })
            whenNotNull(profileConfig, { profileConfig -> whenNull(moviesContext.profileImageConfig, { moviesContext.profileImageConfig = profileConfig }) })
            whenNotNull(movieGenres, { movieGenres -> whenNull(moviesContext.movieGenres, { moviesContext.movieGenres = movieGenres }) })
            whenNotNull(error, { processError(it) })
        }
        backgroundExecutor.executeUiJob { continueToHomeIfConfigReady() }
    }

    private fun continueToHomeIfConfigReady() {
        if (moviesContext.isConfigCompleted()) {
            splashViewInstance.continueToHome()
        }
    }


    private fun processError(error: Error) {
        with(error) {
            if (type == Error.NO_CONNECTION) {
                splashViewInstance.showNotConnectedToNetwork()
            } else {
                splashViewInstance.showUnexpectedError()
            }
        }
    }
}