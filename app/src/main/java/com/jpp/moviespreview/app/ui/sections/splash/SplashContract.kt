package com.jpp.moviespreview.app.ui.sections.splash

import com.jpp.moviespreview.app.ui.Error
import com.jpp.moviespreview.app.ui.PosterImageConfiguration
import com.jpp.moviespreview.app.ui.ProfileImageConfiguration
import kotlin.properties.Delegates

/**
 * Definition of the contract for the MVP implementation at the
 * splash screen
 *
 * Created by jpp on 10/4/17.
 */
interface SplashView {
    fun continueToHome()
    fun showUnexpectedError()
    fun showNotConnectedToNetwork()
}


interface SplashPresenter {
    fun linkView(splashView: SplashView)
}

interface SplashPresenterInteractor {
    fun retrieveConfiguration(splashData: SplashData)
}

/**
 * Defines a communication channel between [SplashPresenter] and [SplashPresenterInteractor].
 * The [SplashPresenter] will ask to the [SplashPresenterInteractor] to execute an action and store
 * the results in this class.
 * The [SplashPresenterInteractor] will execute the action(s) and will set each property of this class.
 * Once the work is done, [isCompleted] is called which, in time, will notify the [SplashPresenter]
 * about the completion of the work using the [onCompleted] function.
 */
class SplashData(private val onCompleted: (Boolean) -> Unit) {
    var isCompleted by Delegates.observable(false) { _, _, new -> onCompleted(new) }
    var posterConfig: List<PosterImageConfiguration>? = null
    var profileConfig: List<ProfileImageConfiguration>? = null
    var error: Error? = null
}