package com.jpp.moviespreview.app.ui.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import javax.inject.Inject

/**
 * Created by jpp on 10/4/17.
 */
class SplashActivity : AppCompatActivity(), SplashView {

    @Inject
    lateinit var splashPresenter : SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun continueToHome() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}