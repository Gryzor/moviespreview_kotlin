package com.jpp.moviespreview.app.ui.interactors

import android.os.Handler
import android.os.Looper
import org.jetbrains.anko.doAsync

/**
 * Executes a given task in a background thread and posts the
 * result into the UI thread.
 *
 * TODO adapt to use co-routines
 *
 * Created by jpp on 2/13/18.
 */
interface BackgroundExecutor {


    fun executeBackgroundJob(backgroundJob: () -> Unit)

    fun executeUiJob(uiJob: () -> Unit)
}


class BackgroundExecutorImpl : BackgroundExecutor {

    private val uiHandler = Handler(Looper.getMainLooper())
    private var isIdle = true

    override fun executeBackgroundJob(backgroundJob: () -> Unit) {
        if (isIdle) {
            isIdle = false
            doAsync {
                backgroundJob()
            }
        }
    }

    override fun executeUiJob(uiJob: () -> Unit) {
        uiHandler.post({
            uiJob()
            isIdle = true
        })
    }

}