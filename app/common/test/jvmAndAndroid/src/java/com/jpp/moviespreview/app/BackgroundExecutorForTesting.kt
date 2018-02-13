package com.jpp.moviespreview.app

import com.jpp.moviespreview.app.ui.interactors.BackgroundExecutor

/**
 * [BackgroundExecutor] implementation for testing purposes only.
 *
 * Created by jpp on 2/13/18.
 */
class BackgroundExecutorForTesting : BackgroundExecutor {

    override fun executeBackgroundJob(backgroundJob: () -> Unit) {
        backgroundJob()
    }

    override fun executeUiJob(uiJob: () -> Unit) {
        uiJob()
    }

}