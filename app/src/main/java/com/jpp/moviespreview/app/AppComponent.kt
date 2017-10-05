package com.jpp.moviespreview.app

import com.jpp.moviespreview.app.ui.splash.di.SplashComponent
import com.jpp.moviespreview.app.ui.splash.di.SplashModule
import dagger.Component
import javax.inject.Singleton

/**
 * Application scope component
 *
 * Created by jpp on 10/4/17.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(app: MoviesPreviewApp)
    fun plus(splashModule: SplashModule) : SplashComponent
}