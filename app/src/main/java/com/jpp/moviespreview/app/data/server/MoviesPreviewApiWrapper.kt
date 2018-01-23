package com.jpp.moviespreview.app.data.server

import com.jpp.moviespreview.app.data.*
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


    /**
     * Retrieves the movie credits from the provided [movieId]
     */
    fun getMovieCredits(movieId: Double): MovieCredits? = apiInstance.getMovieCredits(movieId).execute().body()


    /**
     * Executes a multi search with the provided parameters.
     */
    fun multiSearch(query: String, page: Int): MultiSearchPage? = apiInstance.multiSearch(query, page).execute().body()
}