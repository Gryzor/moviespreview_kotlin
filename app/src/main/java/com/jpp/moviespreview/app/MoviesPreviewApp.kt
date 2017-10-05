package com.jpp.moviespreview.app

import android.app.Application
import com.jpp.moviespreview.app.data.DataModule

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
                .dataModule(DataModule())
                .build()
    }


    override fun onCreate() {
        super.onCreate()
    }

    fun appComponent(): AppComponent = appComponent

}