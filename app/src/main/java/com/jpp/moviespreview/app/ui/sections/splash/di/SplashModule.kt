package com.jpp.moviespreview.app.ui.sections.splash.di

import com.jpp.moviespreview.app.domain.Genre
import com.jpp.moviespreview.app.domain.MoviesConfiguration
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.interactors.BackgroundInteractor
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractor
import com.jpp.moviespreview.app.ui.sections.splash.SplashPresenter
import com.jpp.moviespreview.app.ui.sections.splash.SplashPresenterImpl
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
                                backgroundInteractor: BackgroundInteractor,
                                domainToUiDataMapper: DomainToUiDataMapper,
                                connectivityInteractor: ConnectivityInteractor,
                                configurationUseCase: UseCase<Any, MoviesConfiguration>,
                                genresUseCase: UseCase<Any, List<Genre>>): SplashPresenter
            = SplashPresenterImpl(moviesContext, backgroundInteractor, domainToUiDataMapper, connectivityInteractor, configurationUseCase, genresUseCase)
}