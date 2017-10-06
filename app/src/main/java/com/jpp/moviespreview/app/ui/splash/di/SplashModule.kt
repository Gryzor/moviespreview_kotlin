package com.jpp.moviespreview.app.ui.splash.di

import com.jpp.moviespreview.app.data.cache.MoviesCache
import com.jpp.moviespreview.app.data.server.MoviesPreviewApi
import com.jpp.moviespreview.app.domain.MoviesConfiguration
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.domain.configuration.ConfigurationDataMapper
import com.jpp.moviespreview.app.domain.configuration.RetrieveConfigurationUseCase
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
    fun providesSplashPresenter(useCase: UseCase<Any, MoviesConfiguration>): SplashPresenter = SplashPresenterImpl(useCase)

    @Provides
    @SplashScope
    fun provideRetrieveConfigurationUseCase(apiInstance: MoviesPreviewApi, cache: MoviesCache): UseCase<Any, MoviesConfiguration>
            = RetrieveConfigurationUseCase(ConfigurationDataMapper(), apiInstance, cache)
}