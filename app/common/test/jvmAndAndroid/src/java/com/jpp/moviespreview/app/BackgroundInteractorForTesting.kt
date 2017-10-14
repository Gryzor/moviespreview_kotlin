package com.jpp.moviespreview.app

import com.jpp.moviespreview.app.ui.interactors.BackgroundInteractor

/**
 * BackgroundInteractor implementation for testing purposes.
 *
 * Created by jpp on 10/11/17.
 */
class BackgroundInteractorForTesting : BackgroundInteractor {

    var throwException: Boolean = false

    override fun <T> executeBackgroundJob(backgroundJob: () -> T?,
                                          uiJob: (T?) -> Unit?) {
        val result = backgroundJob()
        if (throwException) {
            uiJob(null)
        } else {
            uiJob(result)
        }
    }

}