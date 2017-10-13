package com.jpp.moviespreview.app

import android.app.Application
import com.jpp.moviespreview.app.data.DataModule
import com.jpp.moviespreview.app.ui.UiModule
import com.jpp.moviespreview.app.ui.splash.di.SplashComponent
import com.jpp.moviespreview.app.ui.splash.di.SplashModule

/**
 * Application class that injects the initial application scope graph
 *
 * Created by jpp on 10/4/17.
 */
open class MoviesPreviewApp : Application() {

    private val appComponent by lazy {
        DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .dataModule(DataModule())
                .uiModule(UiModule())
                .build()
    }


    override fun onCreate() {
        super.onCreate()
    }

    open fun appComponent(): AppComponent = appComponent

    open fun splashComponent() : SplashComponent = appComponent.plus(SplashModule())

}