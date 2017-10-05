package com.jpp.moviespreview.app.ui.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jpp.moviespreview.app.ui.extentions.app
import com.jpp.moviespreview.app.ui.splash.di.SplashModule
import javax.inject.Inject

/**
 * Activity shown on startup. It implements SplashView in order to provide
 * an MVP implementation at this level.
 *
 * The Presenter involved in the splash will attempt to retrieve the initial configuration
 * to continue the application flow.
 *
 * Created by jpp on 10/4/17.
 */
class SplashActivity : AppCompatActivity(), SplashView {

    private val component by lazy { app.appComponent().plus(SplashModule()) }

    @Inject
    lateinit var splashPresenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        splashPresenter.linkView(this)
        splashPresenter.retrieveConfig()
    }

    override fun continueToHome() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}