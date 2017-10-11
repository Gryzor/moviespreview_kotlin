package com.jpp.moviespreview.app.ui.splash

/**
 * Definition of the contract for the MVP implementation at the
 * splash screen
 *
 * Created by jpp on 10/4/17.
 */
interface SplashView {
    fun continueToHome()
    fun showError()
}


interface SplashPresenter {
    fun linkView(splashView: SplashView)
    fun retrieveConfig()
}