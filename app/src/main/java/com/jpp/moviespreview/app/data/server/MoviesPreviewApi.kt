package com.jpp.moviespreview.app.data.server

import com.jpp.moviespreview.BuildConfig
import com.jpp.moviespreview.app.data.Genres
import com.jpp.moviespreview.app.data.MoviePage
import com.jpp.moviespreview.app.data.MoviesConfiguration
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API instance for Retrofit
 *
 * Created by jpp on 10/5/17.
 */
interface MoviesPreviewApi {

    /**
     * Retrieves the last available configuration in the server.
     */
    @GET("configuration")
    fun getLastConfiguration(@Query("api_key") api_key: String = BuildConfig.API_KEY): Call<MoviesConfiguration>


    /**
     * Retrieves the list of genres
     */
    @GET("genre/movie/list")
    fun getGenres(@Query("api_key") api_key: String = BuildConfig.API_KEY): Call<Genres>


    /**
     * Retrieves the list of movies currently playing in theaters.
     * [page] the current page to retrieve.
     * [api_key] the api key provided by themoviedb.
     * [language] Pass a ISO 639-1 value to display translated data for the fields that support it. - Optional.
     * [region] Specify a ISO 3166-1 code to filter release dates. Must be uppercase. - Optional.
     */
    @GET("movie/now_playing")
    fun getNowPlaying(@Query("page") page: Int,
                      @Query("api_key") api_key: String = BuildConfig.API_KEY,
                      @Query("language") language: String? = null,
                      @Query("region") region: String? = null): Call<List<MoviePage>>
}