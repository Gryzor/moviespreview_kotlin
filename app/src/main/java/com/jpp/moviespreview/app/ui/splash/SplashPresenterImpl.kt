package com.jpp.moviespreview.app.ui.splash

import android.util.Log

/**
 * Created by jpp on 10/4/17.
 */
class SplashPresenterImpl : SplashPresenter {

    private lateinit var splashView: SplashView


    override fun linkView(splashView: SplashView) {
        this.splashView = splashView
    }

    override fun retrieveConfig() {
        Log.d("PRESENTER", "RETRIEVE CONFIG")
    }

}