package com.jpp.moviespreview.app.ui.sections.splash

import com.jpp.moviespreview.app.ui.sections.splash.di.SplashActivityComponent

/**
 * Created by jpp on 2/14/18.
 */
class EspressoSplashActivityComponent(private val splashPresenterInstance: SplashPresenter) : SplashActivityComponent {
    override fun injectMembers(instance: SplashActivity?) {
        instance?.let {
            it.splashPresenter = splashPresenterInstance
        }
    }
}