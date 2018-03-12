package com.jpp.moviespreview.app.ui

import android.app.Activity
import com.jpp.moviespreview.app.ui.sections.main.MainActivity
import org.jetbrains.anko.startActivity

/**
 * Resolves the flow between different sections in the application.
 *
 * Created by jpp on 3/12/18.
 */
interface FlowResolver {

    /**
     * Redirects the application to the Main screen.
     */
    fun goToMainScreen()
}


class FlowResolverImpl(private val activity: Activity) : FlowResolver {
    override fun goToMainScreen() {
        activity.startActivity<MainActivity>()
        activity.finish()
    }
}