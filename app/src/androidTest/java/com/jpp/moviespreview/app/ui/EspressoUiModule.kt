package com.jpp.moviespreview.app.ui

import com.jpp.moviespreview.app.BackgroundExecutorForTesting
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
    fun providesMoviesContext() = ApplicationMoviesContext()

    @Singleton
    @Provides
    fun providesBackgroundInteractor(): BackgroundInteractor = BackgroundInteractorForTesting()

    @Singleton
    @Provides
    fun providesBackgroundExecutor(): BackgroundExecutor = BackgroundExecutorForTesting()

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
    fun providesMultiSearchContext(moviesContextHandler: MoviesContextHandler) = MultiSearchContext(moviesContextHandler)

    @Singleton
    @Provides
    fun providesPaginationManager(): PaginationController = PaginationControllerImpl()

    @Singleton
    @Provides
    fun providesMoviesContextHandler(): MoviesContextHandler = mock()
}