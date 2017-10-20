package com.jpp.moviespreview.app

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Application scope module
 *
 * Created by jpp on 10/4/17.
 */
@Module
class AppModule(val app: MoviesPreviewApp) {


    @Singleton
    @Provides
    fun provideApp(): MoviesPreviewApp = app

    @Singleton
    @Provides
    fun providesContext(): Context = app.applicationContext
}