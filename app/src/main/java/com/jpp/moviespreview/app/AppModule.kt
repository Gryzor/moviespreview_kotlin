package com.jpp.moviespreview.app

import android.content.Context
import com.jpp.moviespreview.app.ui.background.BackgroundInteractor
import com.jpp.moviespreview.app.ui.background.BackgroundInteractorImpl
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

    @Provides
    fun providesContext(): Context = app.applicationContext

    @Provides
    fun providesBackgroundInteractor(): BackgroundInteractor = BackgroundInteractorImpl()
}