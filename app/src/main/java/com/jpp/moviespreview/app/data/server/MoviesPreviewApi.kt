package com.jpp.moviespreview.app.data.server

import com.jpp.moviespreview.BuildConfig
import com.jpp.moviespreview.app.data.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
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
                      @Query("region") region: String? = null): Call<MoviePage>


    /**
     * Retrieves the credits of a given movie.
     * [movieId] the identifier of the movie.
     * [api_key] the api key provided by themoviedb.
     */
    @GET("movie/{movie_id}/credits")
    fun getMovieCredits(@Path("movie_id") movieId: Double,
                        @Query("api_key") api_key: String = BuildConfig.API_KEY): Call<MovieCredits>


    /**
     * Executes a multi searchFirstPage API call.
     * [page] the current page to retrieve.
     * [api_key] the api key provided by themoviedb.
     * [language] Pass a ISO 639-1 value to display translated data for the fields that support it. - Optional.
     * [region] Specify a ISO 3166-1 code to filter release dates. Must be uppercase. - Optional.
     * [query] The query to execute.
     */
    @GET("search/multi")
    fun multiSearch(@Query("query") query: String,
                    @Query("page") page: Int,
                    @Query("api_key") api_key: String = BuildConfig.API_KEY,
                    @Query("language") language: String? = null,
                    @Query("region") region: String? = null): Call<MultiSearchPage>
}