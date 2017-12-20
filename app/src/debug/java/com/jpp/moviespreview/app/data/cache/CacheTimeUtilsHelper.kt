package com.jpp.moviespreview.app.data.cache

import java.util.concurrent.TimeUnit

/**
 *
 * Helper class to wrap cache related times functionality
 *
 * Created by jpp on 10/19/17.
 */
class CacheTimeUtilsHelper {


    /**
     * Retrieves the maximum refresh time for the MoviesConfiguration cache.
     */
    fun cacheConfigurationRefreshTime() = TimeUnit.MINUTES.toMillis(30)


    /**
     * Retrieves the maximum refresh time for the MoviesGenre cache.
     */
    fun cacheGenresRefreshTime() = TimeUnit.MINUTES.toMillis(30)


    /**
     * Retrieves the maximum refresh time for the MoviesPage cache.
     */
    fun cacheMoviesPageRefreshTime() = TimeUnit.MINUTES.toMillis(30)


    /**
     * Retrieves the maximum refresh time for the MovieCredits cache.
     */
    fun cacheCreditsMovieRefreshTime() = TimeUnit.MINUTES.toMillis(30)

}