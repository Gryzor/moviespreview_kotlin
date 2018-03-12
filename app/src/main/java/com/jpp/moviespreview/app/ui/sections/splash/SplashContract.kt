package com.jpp.moviespreview.app.ui.sections.splash

import com.jpp.moviespreview.app.ui.Error
import com.jpp.moviespreview.app.ui.MovieGenre
import com.jpp.moviespreview.app.ui.PosterImageConfiguration
import com.jpp.moviespreview.app.ui.ProfileImageConfiguration
import com.jpp.moviespreview.app.util.extentions.DelegatesExt

/**
 * Definition of the contract for the MVP implementation at the
 * splash screen
 *
 * Created by jpp on 10/4/17.
 */
interface SplashView {
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
 * Using the property delegation system ([ObservableTypedDelegate]) the presenter is notified
 * about each property set on this class.
 */
class SplashData(onValueSetObserver: () -> Unit = {}) {
    var posterConfig: List<PosterImageConfiguration>? by DelegatesExt.observerDelegate(onValueSetObserver)
    var profileConfig: List<ProfileImageConfiguration>? by DelegatesExt.observerDelegate(onValueSetObserver)
    var movieGenres: List<MovieGenre>? by DelegatesExt.observerDelegate(onValueSetObserver)
    var error: Error? by DelegatesExt.observerDelegate(onValueSetObserver)
}