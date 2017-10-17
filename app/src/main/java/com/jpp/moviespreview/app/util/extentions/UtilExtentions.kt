package com.jpp.moviespreview.app.util.extentions

/**
 * Created by jpp on 10/6/17.
 */
fun Long.isOlderThan(timestamp: Long): Boolean {
    val now = System.currentTimeMillis()
    return (now - this) > timestamp
}