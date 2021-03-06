package com.jpp.moviespreview.app.ui

import com.jpp.moviespreview.app.BackgroundInteractorForTesting
import com.jpp.moviespreview.app.mock
import com.jpp.moviespreview.app.ui.interactors.*
import com.jpp.moviespreview.app.ui.sections.search.MultiSearchContext
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

    @Singleton
    @Provides
    fun providePresenterInteractorDelegate(backgroundInteractor: BackgroundInteractor,
                                           connectivityInteractor: ConnectivityInteractor): PresenterInteractorDelegate
            = PresenterInteractorDelegateImpl(backgroundInteractor, connectivityInteractor)

    @Singleton
    @Provides
    fun providesMultiSearchContext(moviesContext: MoviesContext) = MultiSearchContext(moviesContext)

    @Singleton
    @Provides
    fun providesPaginationInteractor(): PaginationInteractor = PaginationInteractorImpl()
}