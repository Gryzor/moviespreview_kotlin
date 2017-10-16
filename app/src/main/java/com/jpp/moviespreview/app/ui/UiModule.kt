package com.jpp.moviespreview.app.ui

import android.content.Context
import com.jpp.moviespreview.app.ui.interactors.BackgroundInteractor
import com.jpp.moviespreview.app.ui.interactors.BackgroundInteractorImpl
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractor
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractorImpl
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

    @Singleton
    @Provides
    fun providesConnectivityInteractor(context: Context) : ConnectivityInteractor = ConnectivityInteractorImpl(context)
}