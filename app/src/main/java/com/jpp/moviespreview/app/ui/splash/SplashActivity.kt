package com.jpp.moviespreview.app.ui.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jpp.moviespreview.app.extentions.app
import com.jpp.moviespreview.app.extentions.showNoNetworkConnectionAlert
import com.jpp.moviespreview.app.extentions.showUnexpectedError
import com.jpp.moviespreview.app.ui.main.MainActivity
import org.jetbrains.anko.startActivity
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


    private val component by lazy { app.splashComponent() }

    @Inject
    lateinit var splashPresenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
    }

    override fun onResume() {
        super.onResume()
        splashPresenter.linkView(this)
        splashPresenter.retrieveConfig()
    }

    override fun continueToHome() {
        startActivity<MainActivity>()
        finish()
    }

    override fun showUnexpectedError() {
        showUnexpectedError { finish() }
    }

    override fun showNotConnectedToNetwork() {
        showNoNetworkConnectionAlert()
    }

}