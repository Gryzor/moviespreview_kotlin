package com.jpp.moviespreview.app.ui.sections.splash.di

import com.jpp.moviespreview.app.ui.sections.splash.SplashActivity
import dagger.Subcomponent

/**
 * Splash scope sub-component.
 *
 * Created by jpp on 10/5/17.
 */
@SplashScope
@Subcomponent(modules = arrayOf(SplashModule::class))
interface SplashComponent {

    fun inject(splashActivity: SplashActivity)

}