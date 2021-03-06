package com.jpp.moviespreview.app.ui.sections.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jpp.moviespreview.app.ui.sections.main.MainActivity
import com.jpp.moviespreview.app.util.extentions.app
import com.jpp.moviespreview.app.util.extentions.showNoNetworkConnectionAlert
import com.jpp.moviespreview.app.util.extentions.showUnexpectedError
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