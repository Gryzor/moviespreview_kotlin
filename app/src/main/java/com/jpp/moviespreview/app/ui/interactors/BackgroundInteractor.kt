package com.jpp.moviespreview.app.ui.interactors

import android.os.Handler
import android.os.Looper
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Generic interactor definition to execute background jobs.
 *
 * Created by jpp on 10/7/17.
 */
interface BackgroundInteractor {

    /**
     * Executes a [backgroundJob] job in a background thread and the [uiJob] in the main UI thread.
     * If an exception is thrown while executing the [backgroundJob], the [uiJob] is executed
     * with a null argument and the exception is logged.
     */
    fun <T> executeBackgroundJob(backgroundJob: () -> T?,
                                 uiJob: (T?) -> Unit?)
}


/**
 * BackgroundInteractor implementation to use the doAsyn() Kotlin extension.
 */
class BackgroundInteractorImpl : BackgroundInteractor {


    override fun <T> executeBackgroundJob(backgroundJob: () -> T?,
                                          uiJob: (T?) -> Unit?) {
        doAsync(exceptionHandler = { manageExceptionOnUiThread(uiJob, it) }) {
            val response = backgroundJob()
            uiThread {
                uiJob(response)
            }
        }
    }

    private fun <T> manageExceptionOnUiThread(uiJob: (T?) -> Unit?,
                                              throwable: Throwable) {
        //TODO log the error to crashlytics
        throwable.printStackTrace()
        val uiHandler = Handler(Looper.getMainLooper())
        uiHandler.post({ uiJob(null) })
    }
}