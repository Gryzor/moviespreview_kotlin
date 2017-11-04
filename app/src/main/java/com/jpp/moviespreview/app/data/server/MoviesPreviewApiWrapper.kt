package com.jpp.moviespreview.app.data.server

import com.jpp.moviespreview.app.data.Genres
import com.jpp.moviespreview.app.data.MoviePage
import com.jpp.moviespreview.app.data.MoviesConfiguration
import com.jpp.moviespreview.app.util.AllOpen

/**
 * Wrapper created around the Retrofit implementation of the API interface.
 *
 * DESIGN: do not use the API Retrofit implementation directly, use this one instead.
 *
 * Created by jpp on 10/11/17.
 */
@AllOpen
class MoviesPreviewApiWrapper(private val apiInstance: MoviesPreviewApi) {

    /**
     * Retrieves the last available configuration in the server.
     */
    fun getLastMovieConfiguration(): MoviesConfiguration? = apiInstance.getLastConfiguration().execute().body()

    /**
     * Retrieves the list of Genre available in the server.
     */
    fun getGenres(): Genres? = apiInstance.getGenres().execute().body()


    /**
     * Retrieves the provided [page] of movies currently playing on theaters.
     */
    fun getNowPlaying(page: Int): MoviePage? = apiInstance.getNowPlaying(page).execute().body()
}