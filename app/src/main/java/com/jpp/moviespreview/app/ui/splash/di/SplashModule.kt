package com.jpp.moviespreview.app.ui.splash.di

import com.jpp.moviespreview.app.data.cache.MoviesCache
import com.jpp.moviespreview.app.data.server.MoviesPreviewApiWrapper
import com.jpp.moviespreview.app.domain.MoviesConfiguration
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.domain.configuration.ConfigurationDataMapper
import com.jpp.moviespreview.app.domain.configuration.RetrieveConfigurationUseCase
import com.jpp.moviespreview.app.extentions.TimeUtils
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.interactors.BackgroundInteractor
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractor
import com.jpp.moviespreview.app.ui.splash.SplashPresenter
import com.jpp.moviespreview.app.ui.splash.SplashPresenterImpl
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
    fun providesSplashPresenter(useCase: UseCase<Any, MoviesConfiguration>,
                                backgroundInteractor: BackgroundInteractor,
                                moviesContext: MoviesContext,
                                domainToUiDataMapper: DomainToUiDataMapper,
                                connectivityInteractor: ConnectivityInteractor): SplashPresenter
            = SplashPresenterImpl(useCase, backgroundInteractor, moviesContext, domainToUiDataMapper, connectivityInteractor)

    @Provides
    @SplashScope
    fun provideRetrieveConfigurationUseCase(apiInstance: MoviesPreviewApiWrapper, cache: MoviesCache, timeUtils: TimeUtils): UseCase<Any, MoviesConfiguration>
            = RetrieveConfigurationUseCase(ConfigurationDataMapper(), apiInstance, cache, timeUtils)
}