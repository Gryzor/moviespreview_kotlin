package com.jpp.moviespreview.app.ui.background

import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by jpp on 10/7/17.
 */
interface BackgroundInteractor {
    fun <T> executeBackgroundJob(backgroundJob: () -> T?,
                                 uiJob: (T?) -> Unit?,
                                 exceptionHandler: ((Throwable) -> Unit))
}


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