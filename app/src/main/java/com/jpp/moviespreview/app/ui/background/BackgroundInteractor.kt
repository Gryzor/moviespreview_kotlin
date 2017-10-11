package com.jpp.moviespreview.app.ui.background

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
     * If an exception is thrown while executing the [backgroundJob], the [exceptionHandler] will
     * be notified.
     */
    fun <T> executeBackgroundJob(backgroundJob: () -> T?,
                                 uiJob: (T?) -> Unit?,
                                 exceptionHandler: ((Throwable) -> Unit))
}


/**
 * BackgroundInteractor implementation to use the doAsyn() Kotlin extension.
 */
class BackgroundInteractorImpl : BackgroundInteractor {


    override fun <T> executeBackgroundJob(backgroundJob: () -> T?,
                                          uiJob: (T?) -> Unit?,
                                          exceptionHandler: (Throwable) -> Unit) {

        doAsync(exceptionHandler = exceptionHandler) {
            val response = backgroundJob()
            uiThread {
                uiJob(response)
            }
        }
    }
}