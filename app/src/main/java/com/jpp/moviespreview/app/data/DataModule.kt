package com.jpp.moviespreview.app.data

import com.jpp.moviespreview.BuildConfig
import com.jpp.moviespreview.app.data.server.MoviesPreviewApi
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
    fun providesServerApi(): MoviesPreviewApi = API

}