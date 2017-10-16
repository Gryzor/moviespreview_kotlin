package com.jpp.moviespreview.app.data

import com.jpp.moviespreview.app.data.cache.CacheDataMapper
import com.jpp.moviespreview.app.data.cache.MoviesCache
import com.jpp.moviespreview.app.data.cache.db.MoviesDataBase
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
    fun providesCacheDataMapper(): CacheDataMapper = CacheDataMapper()

    @Provides
    @Singleton
    fun providesMoviesCache(): MoviesCache = mock()

    @Provides
    @Singleton
    fun providesServerApi(): MoviesPreviewApiWrapper = mock()
}