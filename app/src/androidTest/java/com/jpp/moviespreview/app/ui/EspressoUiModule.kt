package com.jpp.moviespreview.app.ui

import com.jpp.moviespreview.app.BackgroundInteractorForTesting
import com.jpp.moviespreview.app.mock
import com.jpp.moviespreview.app.ui.interactors.BackgroundInteractor
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractor
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
    fun providesMoviesContext() = MoviesContext()

    @Singleton
    @Provides
    fun providesBackgroundInteractor(): BackgroundInteractor = BackgroundInteractorForTesting()

    @Singleton
    @Provides
    fun providesDomainToUiDataMapper() = DomainToUiDataMapper()

    @Singleton
    @Provides
    fun providesConnectivityInteractor(): ConnectivityInteractor = mock()
}