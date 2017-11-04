package com.jpp.moviespreview.app.data

import android.arch.persistence.room.Room
import android.content.Context
import com.jpp.moviespreview.BuildConfig
import com.jpp.moviespreview.app.data.cache.*
import com.jpp.moviespreview.app.data.cache.db.MoviesDataBase
import com.jpp.moviespreview.app.data.server.MoviesPreviewApi
import com.jpp.moviespreview.app.data.server.MoviesPreviewApiWrapper
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Dagger module to provide data layer instances
 *
 * Created by jpp on 10/5/17.
 */
@Module
class DataModule {

    companion object {
        val API: MoviesPreviewApi by lazy {
            // create Retrofit instance
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BuildConfig.API_ENDPOINT)
                    .client(client)
                    .build()
            // create API instance
            retrofit.create(MoviesPreviewApi::class.java)
        }
    }

    @Provides
    @Singleton
    fun providesMovieDatabase(context: Context): MoviesDataBase =
            Room.databaseBuilder(context, MoviesDataBase::class.java, "movies-db")
                    .allowMainThreadQueries()
                    .build()

    @Provides
    @Singleton
    fun providesServerApi(): MoviesPreviewApiWrapper = MoviesPreviewApiWrapper(API)


    @Provides
    @Singleton
    fun providesCacheTimeUtils() = CacheTimestampUtils(CacheTimeUtilsHelper())

    @Provides
    @Singleton
    fun providesCacheDataMapper() = CacheDataMapper()


    @Provides
    @Singleton
    fun providesMoviesConfigurationCache(mapper: CacheDataMapper,
                                         moviesDataBase: MoviesDataBase,
                                         cacheTimestampUtils: CacheTimestampUtils): MoviesConfigurationCache = MoviesConfigurationCacheImpl(mapper, moviesDataBase, cacheTimestampUtils)


    @Provides
    @Singleton
    fun providesMoviesGenreCache(mapper: CacheDataMapper,
                                 moviesDataBase: MoviesDataBase,
                                 cacheTimestampUtils: CacheTimestampUtils): MoviesGenreCache = MoviesGenreCacheImpl(mapper, moviesDataBase, cacheTimestampUtils)


    @Provides
    @Singleton
    fun providesMoviesCache(mapper: CacheDataMapper,
                            moviesDataBase: MoviesDataBase,
                            cacheTimestampUtils: CacheTimestampUtils): MoviesCache = MoviesCacheImpl(mapper, moviesDataBase, cacheTimestampUtils)
}