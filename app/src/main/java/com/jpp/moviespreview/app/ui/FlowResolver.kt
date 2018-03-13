package com.jpp.moviespreview.app.ui

import android.app.Activity
import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailActivity
import com.jpp.moviespreview.app.ui.sections.main.MainActivity
import com.jpp.moviespreview.app.ui.sections.splash.SplashActivity
import org.jetbrains.anko.startActivity

/**
 * Resolves the flow between different sections in the application.
 *
 * Created by jpp on 3/12/18.
 */
interface FlowResolver {

    /**
     * Redirects the application to the splash screen
     */
    fun goToSplashScreen()

    /**
     * Redirects the application to the Main screen.
     */
    fun goToMainScreen()

    /**
     * Redirects the application to the Movie details screen.
     */
    fun goToDetailsScreen()
}


class FlowResolverImpl(private val activity: Activity) : FlowResolver {

    override fun goToSplashScreen() {
        activity.startActivity<SplashActivity>()
        activity.finish()
    }

    override fun goToMainScreen() {
        activity.startActivity<MainActivity>()
        activity.finish()
    }

    override fun goToDetailsScreen() {
        activity.startActivity<MovieDetailActivity>()
    }
}