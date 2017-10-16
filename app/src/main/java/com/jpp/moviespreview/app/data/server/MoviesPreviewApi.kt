package com.jpp.moviespreview.app.data.server

import com.jpp.moviespreview.app.data.Genres
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
    fun getLastConfiguration(@Query("api_key") api_key: String): Call<MoviesConfiguration>


    /**
     * Retrieves the list of genres
     */
    @GET("/genre/movie/list")
    fun getRenges(@Query("api_key") api_key: String): Call<Genres>
}