package com.jpp.moviespreview.app.ui

import android.content.Context
import com.jpp.moviespreview.app.ui.interactors.*
import com.jpp.moviespreview.app.ui.sections.search.MultiSearchContext
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
    fun providesMoviesContext() = ApplicationMoviesContext()

    @Singleton
    @Provides
    fun providesMultiSearchContext(moviesContextHandler: MoviesContextHandler) = MultiSearchContext(moviesContextHandler)


    @Singleton
    @Provides
    fun providesDomainToUiDataMapper() = DomainToUiDataMapper()

    @Singleton
    @Provides
    fun providesBackgroundInteractor(): BackgroundInteractor = BackgroundInteractorImpl()

    @Singleton
    @Provides
    fun providesMoviesContextHandler(moviesContext: ApplicationMoviesContext): MoviesContextHandler = MoviesContextHandlerImpl(moviesContext)

    @Singleton
    @Provides
    fun providesBackgroundExecutor(): BackgroundExecutor = BackgroundExecutorImpl()


    @Singleton
    @Provides
    fun providesConnectivityInteractor(context: Context): ConnectivityInteractor = ConnectivityInteractorImpl(context)

    @Singleton
    @Provides
    fun providePresenterInteractorDelegate(backgroundInteractor: BackgroundInteractor,
                                           connectivityInteractor: ConnectivityInteractor): PresenterInteractorDelegate = PresenterInteractorDelegateImpl(backgroundInteractor, connectivityInteractor)


    @Singleton
    @Provides
    fun providesPaginationManager(): PaginationController = PaginationControllerImpl()
}