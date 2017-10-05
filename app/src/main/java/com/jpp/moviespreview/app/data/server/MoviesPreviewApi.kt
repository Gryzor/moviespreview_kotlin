package com.jpp.moviespreview.app.data.server

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

    @GET("configuration")
    fun getLastConfiguration(@Query("api_key") api_key: String): Call<MoviesConfiguration>
}