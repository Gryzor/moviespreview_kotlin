package com.jpp.moviespreview.app.util

import com.jpp.moviespreview.app.util.extentions.isOlderThan

/**
 * Created by jpp on 10/11/17.
 */
class TimeUtils {

    fun currentTimeInMillis() = System.currentTimeMillis()

    fun isOlderThan(value: Long, than: Long): Boolean = value.isOlderThan(than)

}