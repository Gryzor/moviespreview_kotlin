package com.jpp.moviespreview.app

import android.app.Application

/**
 * Application class that injects the initial application scope graph
 *
 * Created by jpp on 10/4/17.
 */
class MoviesPreviewApp : Application() {

    private val appComponent by lazy {
        DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }


    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }

    fun appComponent(): AppComponent = appComponent

}