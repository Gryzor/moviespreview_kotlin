package com.jpp.moviespreview.app.ui.splash

import android.util.Log
import com.jpp.moviespreview.app.domain.MoviesConfiguration
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.ui.background.BackgroundInteractor
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by jpp on 10/4/17.
 */
class SplashPresenterImpl(private val useCase: UseCase<Any, MoviesConfiguration>,
                          private val backgroundInteractor: BackgroundInteractor) : SplashPresenter {

    private lateinit var splashView: SplashView


    override fun linkView(splashView: SplashView) {
        this.splashView = splashView
    }

    override fun retrieveConfig() {
        backgroundInteractor
                .executeBackgroundJob({ useCase.execute() },
                        { processMoviesConfig(it) },
                        { processMoviesConfigError(it) })
    }


    private fun processMoviesConfig(moviesConfiguration: MoviesConfiguration?) {
        if (moviesConfiguration != null) {
            Log.d("PRESENTER", "Result data " + moviesConfiguration.imagesConfiguration.baseUrl)
        } else {
            Log.d("PRESENTER", "NOTHING MEN")
        }
    }

    private fun processMoviesConfigError(error: Throwable) {
        Log.e("PRESENTER", "ERROR")
    }

}