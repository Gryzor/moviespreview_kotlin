package com.jpp.moviespreview.app

import dagger.Module
import dagger.Provides

/**
 * Application scope module
 *
 * Created by jpp on 10/4/17.
 */
@Module
class AppModule(val app: MoviesPreviewApp) {

    @Provides
    fun provideApp(): MoviesPreviewApp = app
}