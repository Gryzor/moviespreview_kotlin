package com.jpp.moviespreview.app.data.cache

import android.annotation.SuppressLint
import android.support.annotation.VisibleForTesting
import com.jpp.moviespreview.app.data.cache.db.Timestamp
import com.jpp.moviespreview.app.data.cache.db.TimestampDao

/**
 * Timestamp utility class for the cache module.
 * The main responsibility is to manage all [Timestamp]s that
 * are used by the cache.
 *
 * Created by jpp on 10/19/17.
 */
class CacheTimestampUtils(private val helper: CacheTimeUtilsHelper) {

    companion object TimestampFactory {
        private val moviesConfigurationId = 1L
        private val moviesGenresId = 2L
        private val moviesPageId = 3L
    }

    /**
     * Creates the Timestamp that represents the movies configuration
     */
    fun createMoviesConfigurationTimestamp() = Timestamp(moviesConfigurationId, currentTimeInMillis())

    /**
     * Creates the Timestamp that represents the movies genres
     */
    fun createMovieGenresTimestamp() = Timestamp(moviesGenresId, currentTimeInMillis())

    /**
     * Creates the Timestamp that represents the Movies [page]
     */
    fun createMoviePageTimestamp(page: Int) = Timestamp(moviesPageId, currentTimeInMillis(), page)

    /**
     * Wrap currentTimeInMillis method.
     */
    private fun currentTimeInMillis() = System.currentTimeMillis()

    /**
     * Verifies if the Movies Configuration timestamp stored in the database is out of date.
     */
    @SuppressLint("VisibleForTests")
    fun isConfigurationTimestampOutdated(timestampDao: TimestampDao): Boolean {
        val retrievesMoviesConfigurationTimestamp = timestampDao.getTimestamp(moviesConfigurationId)
        return isTimestampOutdated(retrievesMoviesConfigurationTimestamp, helper.cacheConfigurationRefreshTime())
    }

    /**
     * Verifies if the Movies Genres timestamp stored in the database is out of date.
     */
    @SuppressLint("VisibleForTests")
    fun isMoviesGenreTimestampOutdated(timestampDao: TimestampDao): Boolean {
        val retrievesMoviesGenresTimestamp = timestampDao.getTimestamp(moviesGenresId)
        return isTimestampOutdated(retrievesMoviesGenresTimestamp, helper.cacheGenresRefreshTime())
    }


    /**
     * Verifies if the MoviePage timestamp stored for the provided [page] is out of date.
     */
    @SuppressLint("VisibleForTests")
    fun isMoviePageTimestampOutdated(timestampDao: TimestampDao, page: Int): Boolean {
        val moviesPageTimestamp = timestampDao.getTimestamp(moviesPageId, page)
        return isTimestampOutdated(moviesPageTimestamp, helper.cacheMoviesPageRefreshTime())
    }


    /**
     * Determinate if the provided [timestamp] is outdated based on the [refreshTime].
     */
    @VisibleForTesting
    fun isTimestampOutdated(timestamp: Timestamp?, refreshTime: Long): Boolean {
        var outdated = true

        if (timestamp != null) {
            val now = currentTimeInMillis()
            outdated = (now - timestamp.lastUpdate) > refreshTime
        }

        return outdated
    }

}