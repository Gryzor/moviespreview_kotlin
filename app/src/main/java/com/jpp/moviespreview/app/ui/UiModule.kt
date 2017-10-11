package com.jpp.moviespreview.app.ui

import com.jpp.moviespreview.app.ui.background.BackgroundInteractor
import com.jpp.moviespreview.app.ui.background.BackgroundInteractorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger 2 module to inject UI layer dependencies.
 *
 * Created by jpp on 10/11/17.
 */
@Module
class UiModule {

    @Singleton
    @Provides
    fun providesMoviesContext() = MoviesContext()

    @Singleton
    @Provides
    fun providesBackgroundInteractor(): BackgroundInteractor = BackgroundInteractorImpl()

    @Singleton
    @Provides
    fun providesDomainToUiDataMapper() = DomainToUiDataMapper()
}