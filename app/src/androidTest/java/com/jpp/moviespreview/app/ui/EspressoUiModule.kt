package com.jpp.moviespreview.app.ui

import com.jpp.moviespreview.app.BackgroundInteractorForTesting
import com.jpp.moviespreview.app.mock
import com.jpp.moviespreview.app.ui.background.BackgroundInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Provides the UiModule implementation for Espresso tests
 *
 * Created by jpp on 10/13/17.
 */
@Module
class EspressoUiModule {

    @Singleton
    @Provides
    fun providesMoviesContext(): MoviesContext = mock()

    @Singleton
    @Provides
    fun providesBackgroundInteractor(): BackgroundInteractor = BackgroundInteractorForTesting()

    @Singleton
    @Provides
    fun providesDomainToUiDataMapper() = DomainToUiDataMapper()
}