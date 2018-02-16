package com.jpp.moviespreview.app

import com.jpp.moviespreview.app.ui.interactors.BackgroundExecutor

/**
 * [BackgroundExecutor] implementation for testing purposes only.
 *
 * Created by jpp on 2/13/18.
 */
class BackgroundExecutorForTesting : BackgroundExecutor {

    var backgroundExecuted = false

    override fun executeBackgroundJob(backgroundJob: () -> Unit) {
        backgroundJob()
        backgroundExecuted = true
    }

    override fun executeUiJob(uiJob: () -> Unit) {
        uiJob()
    }

}