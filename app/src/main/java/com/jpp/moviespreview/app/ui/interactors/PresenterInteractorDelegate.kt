package com.jpp.moviespreview.app.ui.interactors

/**
 * Delegate definition to manage the interactor helpers execution.
 * The main purpose of this class is to simplify testing the Presenter.
 *
 * Created by jpp on 10/28/17.
 */

interface PresenterInteractorDelegate : BackgroundInteractor, ConnectivityInteractor

class PresenterInteractorDelegateImpl(private val backgroundInteractor: BackgroundInteractor,
                                      private val connectivityInteractor: ConnectivityInteractor) : PresenterInteractorDelegate {
    override fun isIdle() = backgroundInteractor.isIdle()

    override fun isConnectedToNetwork() = connectivityInteractor.isConnectedToNetwork()

    override fun <T> executeBackgroundJob(backgroundJob: () -> T?, uiJob: (T?) -> Unit?) {
        backgroundInteractor.executeBackgroundJob(backgroundJob, uiJob)
    }

}