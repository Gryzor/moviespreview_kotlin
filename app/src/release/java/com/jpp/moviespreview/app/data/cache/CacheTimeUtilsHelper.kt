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
    fun cacheConfigurationRefreshTime() = TimeUnit.DAYS.toMillis(7)


    /**
     * Retrieves the maximum refresh time for the MoviesGenre cache.
     */
    fun cacheGenresRefreshTime() = TimeUnit.DAYS.toMillis(7)

    /**
     * Retrieves the maximum refresh time for the MoviesPage cache.
     */
    fun cacheMoviesPageRefreshTime() = TimeUnit.HOURS.toMillis(1)

}