package com.jpp.moviespreview.app.ui.splash

import android.util.Log
import com.jpp.moviespreview.app.domain.MoviesConfiguration
import com.jpp.moviespreview.app.domain.UseCase

/**
 * Created by jpp on 10/4/17.
 */
class SplashPresenterImpl(private val useCase: UseCase<Any, MoviesConfiguration>) : SplashPresenter {

    private lateinit var splashView: SplashView


    override fun linkView(splashView: SplashView) {
        this.splashView = splashView
        useCase.execute(null)
    }

    override fun retrieveConfig() {
        //TODO useCase.execute(null)
    }

}