package com.jpp.moviespreview.app.ui.interactors

import android.os.Handler
import android.os.Looper
import org.jetbrains.anko.doAsync

/**
 * Created by jpp on 2/13/18.
 */
interface BackgroundExecutor {


    fun executeBackgroundJob(backgroundJob: () -> Unit)

    fun executeUiJob(uiJob: () -> Unit)
}


class BackgroundExecutorImpl : BackgroundExecutor {

    private val uiHandler = Handler(Looper.getMainLooper())

    override fun executeBackgroundJob(backgroundJob: () -> Unit) {
        doAsync {
            backgroundJob()
        }
    }

    override fun executeUiJob(uiJob: () -> Unit) {
        uiHandler.post(uiJob)
    }

}