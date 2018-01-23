package com.jpp.moviespreview.app

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by jpp on 10/13/17.
 */
@Module
class EspressoAppModule(val app: MoviesPreviewApp) {


    @Singleton
    @Provides
    fun provideApp(): MoviesPreviewApp = app

    @Singleton
    @Provides
    fun providesContext(): Context = app.applicationContext
}