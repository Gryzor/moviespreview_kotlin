package com.jpp.moviespreview.app.ui.main.playing

import com.jpp.moviespreview.app.ui.interactors.BackgroundInteractor
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractor

/**
 * Delegate definition to manage the interactor helpers execution.
 * The main purpose of this class is to simplify testing the Presenter.
 *
 * Created by jpp on 10/28/17.
 */

interface PlayingMoviesInteractorDelegate : BackgroundInteractor, ConnectivityInteractor

class PlayingMoviesInteractorDelegateImpl(private val backgroundInteractor: BackgroundInteractor,
                                          private val connectivityInteractor: ConnectivityInteractor) : PlayingMoviesInteractorDelegate {
    override fun isConnectedToNetwork() = connectivityInteractor.isConnectedToNetwork()

    override fun <T> executeBackgroundJob(backgroundJob: () -> T?, uiJob: (T?) -> Unit?) {
        backgroundInteractor.executeBackgroundJob(backgroundJob, uiJob)
    }

}