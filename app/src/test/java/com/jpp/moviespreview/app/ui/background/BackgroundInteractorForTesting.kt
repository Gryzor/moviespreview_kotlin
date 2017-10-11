package com.jpp.moviespreview.app.ui.background

/**
 * BackgroundInteractor implementation for testing purposes.
 *
 * Created by jpp on 10/11/17.
 */
class BackgroundInteractorForTesting : BackgroundInteractor {

    var throwException: Boolean = false

    override fun <T> executeBackgroundJob(backgroundJob: () -> T?,
                                          uiJob: (T?) -> Unit?,
                                          exceptionHandler: (Throwable) -> Unit) {
        val result = backgroundJob()
        if (throwException) {
            exceptionHandler(Exception())
        } else {
            uiJob(result)
        }
    }

}