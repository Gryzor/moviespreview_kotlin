package com.jpp.moviespreview.app.ui.splash

import android.util.Log
import com.jpp.moviespreview.app.domain.MoviesConfiguration
import com.jpp.moviespreview.app.domain.UseCase
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by jpp on 10/4/17.
 */
class SplashPresenterImpl(private val useCase: UseCase<Any, MoviesConfiguration>) : SplashPresenter {

    private lateinit var splashView: SplashView


    override fun linkView(splashView: SplashView) {
        this.splashView = splashView
    }

    override fun retrieveConfig() {
        doAsync {
            val result = useCase.execute(null)
            uiThread {
                Log.d("PRESENTER", "Result data " + result!!.imagesConfiguration.baseUrl)
            }
        }
    }

}