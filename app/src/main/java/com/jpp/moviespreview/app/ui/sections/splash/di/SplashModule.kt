package com.jpp.moviespreview.app.ui.sections.splash.di

import com.jpp.moviespreview.app.domain.Command
import com.jpp.moviespreview.app.domain.Genre
import com.jpp.moviespreview.app.domain.MoviesConfiguration
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.interactors.BackgroundExecutor
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractor
import com.jpp.moviespreview.app.ui.sections.splash.SplashPresenter
import com.jpp.moviespreview.app.ui.sections.splash.SplashPresenterImpl
import com.jpp.moviespreview.app.ui.sections.splash.SplashPresenterInteractor
import com.jpp.moviespreview.app.ui.sections.splash.SplashPresenterInteractorImpl
import dagger.Module
import dagger.Provides

/**
 * Splash scope module.
 *
 * Created by jpp on 10/4/17.
 */
@Module
class SplashModule {

    @Provides
    @SplashScope
    fun providesSplashPresenter(moviesContext: MoviesContext,
                                backgroundExecutor: BackgroundExecutor,
                                interactor: SplashPresenterInteractor): SplashPresenter
            = SplashPresenterImpl(moviesContext, backgroundExecutor, interactor)

    @Provides
    @SplashScope
    fun providesSplashPresenterInteractor(mapper: DomainToUiDataMapper,
                                          connectivityInteractor: ConnectivityInteractor,
                                          retrieveConfigurationCommand: Command<Any, MoviesConfiguration>,
                                          retrieveGenresCommand: Command<Any, List<Genre>>): SplashPresenterInteractor
            = SplashPresenterInteractorImpl(mapper, connectivityInteractor, retrieveConfigurationCommand, retrieveGenresCommand)
}