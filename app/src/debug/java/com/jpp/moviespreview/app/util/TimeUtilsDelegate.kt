package com.jpp.moviespreview.app.util

import java.util.concurrent.TimeUnit

/**
 * Created by jpp on 10/18/17.
 */
class TimeUtilsDelegate {

    fun cacheConfigurationRefreshTime() = TimeUnit.MINUTES.toMillis(30)

}