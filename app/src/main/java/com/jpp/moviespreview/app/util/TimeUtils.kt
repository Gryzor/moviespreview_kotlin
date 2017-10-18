package com.jpp.moviespreview.app.util

import com.jpp.moviespreview.app.util.extentions.isOlderThan

/**
 * Provides utilities related to time and dates
 *
 * Created by jpp on 10/11/17.
 */
@AllOpen
class TimeUtils(private val delegate: TimeUtilsDelegate) {

    /**
     * Wrap currentTimeInMillis method.
     */
    fun currentTimeInMillis() = System.currentTimeMillis()


    /**
     * Determinate if the [value] provided is older [than]
     */
    fun isOlderThan(value: Long, than: Long): Boolean = value.isOlderThan(than)


    /**
     * Retrieves the refresh time for the configuration cache.
     */
    fun cacheConfigurationRefreshTime() = delegate.cacheConfigurationRefreshTime()

}