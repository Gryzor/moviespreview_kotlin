package com.jpp.moviespreview.app.ui.splash.di

import com.jpp.moviespreview.app.data.cache.MoviesConfigurationCache
import com.jpp.moviespreview.app.data.cache.genre.MoviesGenreCache
import com.jpp.moviespreview.app.data.server.MoviesPreviewApiWrapper
import com.jpp.moviespreview.app.domain.Genre
import com.jpp.moviespreview.app.domain.MoviesConfiguration
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.domain.configuration.ConfigurationDataMapper
import com.jpp.moviespreview.app.domain.configuration.RetrieveConfigurationUseCase
import com.jpp.moviespreview.app.domain.genre.GenreDataMapper
import com.jpp.moviespreview.app.domain.genre.RetrieveGenresUseCase
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
                                genresUseCase: UseCase<Any, List<Genre>>,
                                backgroundInteractor: BackgroundInteractor,
                                moviesContext: MoviesContext,
                                domainToUiDataMapper: DomainToUiDataMapper,
                                connectivityInteractor: ConnectivityInteractor): SplashPresenter
            = SplashPresenterImpl(useCase, genresUseCase, backgroundInteractor, moviesContext, domainToUiDataMapper, connectivityInteractor)

    @Provides
    @SplashScope
    fun provideRetrieveConfigurationUseCase(apiInstance: MoviesPreviewApiWrapper, configurationCache: MoviesConfigurationCache): UseCase<Any, MoviesConfiguration>
            = RetrieveConfigurationUseCase(ConfigurationDataMapper(), apiInstance, configurationCache)


    @Provides
    @SplashScope
    fun provideRetrieveMovieGenresUseCase(apiInstance: MoviesPreviewApiWrapper, genresCache: MoviesGenreCache): UseCase<Any, List<Genre>>
            = RetrieveGenresUseCase(GenreDataMapper(), apiInstance, genresCache)
}