package com.jpp.moviespreview.app.data

import com.jpp.moviespreview.app.data.cache.CacheTimestampUtils
import com.jpp.moviespreview.app.data.cache.configuration.MoviesConfigurationCache
import com.jpp.moviespreview.app.data.cache.configuration.MoviesConfigurationCacheDataMapper
import com.jpp.moviespreview.app.data.cache.db.MoviesDataBase
import com.jpp.moviespreview.app.data.cache.genre.MoviesGenreCache
import com.jpp.moviespreview.app.data.cache.genre.MoviesGenreCacheDataMapper
import com.jpp.moviespreview.app.data.server.MoviesPreviewApiWrapper
import com.jpp.moviespreview.app.mock
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Provides the DataModule implementation for espresso tests
 *
 * Created by jpp on 10/13/17.
 */
@Module
class EspressoDataModule {

    @Provides
    @Singleton
    fun providesMovieDatabase(): MoviesDataBase = mock()

    @Provides
    @Singleton
    fun providesServerApi(): MoviesPreviewApiWrapper = mock()

    @Provides
    @Singleton
    fun providesCacheTimeUtils(): CacheTimestampUtils = mock()

    @Provides
    @Singleton
    fun providesMoviesConfigurationCacheDataMapper(): MoviesConfigurationCacheDataMapper = MoviesConfigurationCacheDataMapper()

    @Provides
    @Singleton
    fun providesMoviesConfigurationCache(): MoviesConfigurationCache = mock()

    @Provides
    @Singleton
    fun providesMoviesGenresCacheDataMapper() = MoviesGenreCacheDataMapper()


    @Provides
    @Singleton
    fun providesMoviesGenreCache(): MoviesGenreCache = mock()


}