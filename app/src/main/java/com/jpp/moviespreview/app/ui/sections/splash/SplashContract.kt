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


class SplashData(private val onCompleted: (Boolean) -> Unit) {
    var isCompleted by Delegates.observable(false) { _, _, new -> onCompleted(new) }
    var posterConfig: List<PosterImageConfiguration>? = null
    var profileConfig: List<ProfileImageConfiguration>? = null
    var error: Error? = null
}