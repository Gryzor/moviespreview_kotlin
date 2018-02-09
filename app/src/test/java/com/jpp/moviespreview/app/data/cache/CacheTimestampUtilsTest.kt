package com.jpp.moviespreview.app.data.cache

import com.jpp.moviespreview.app.assertFalseIfNotNull
import com.jpp.moviespreview.app.assertTrueIfNotNull
import com.jpp.moviespreview.app.data.cache.db.Timestamp
import com.jpp.moviespreview.app.data.cache.db.TimestampDao
import com.jpp.moviespreview.app.mock
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import java.util.concurrent.TimeUnit

/**
 * Created by jpp on 10/19/17.
 */
class CacheTimestampUtilsTest {

    lateinit var helper: CacheTimeUtilsHelper
    lateinit var subject: CacheTimestampUtils

    @Before
    fun setup() {
        helper = mock()
        subject = CacheTimestampUtils(helper)
    }


    @Test
    fun isTimestampOutdated() {
        val oneDayTimestamp = Timestamp(1, System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1))
        // a one day timestamp should be outdated if refresh time is two hours
        assertTrueIfNotNull(subject.isTimestampOutdated(oneDayTimestamp, TimeUnit.HOURS.toMillis(2)))

        // a one day timestamp should NOT be outdated if refresh time is 28 hours
        assertFalseIfNotNull(subject.isTimestampOutdated(oneDayTimestamp, TimeUnit.HOURS.toMillis(28)))

        // null check -> null
        assertNull(subject.isTimestampOutdated(null, TimeUnit.DAYS.toMillis(1)))
    }


    @Test
    fun isConfigurationTimestampOutdated_retrievesDataFromDao_andVeirfiesUpdated() {
        val moviesConfigTimestamp = subject.createMoviesConfigurationTimestamp()
        val timestampDao: TimestampDao = mock()

        val result = subject.isConfigurationTimestampOutdated(timestampDao)

        verify(timestampDao).getTimestamp(moviesConfigTimestamp.id)
        assertTrue(result)
    }

    @Test
    fun isMoviesGenreTimestampOutdated_retrievesDataFromDao_andVeirfiesUpdated() {
        val movieGenresTimestamp = subject.createMovieGenresTimestamp()
        val timestampDao: TimestampDao = mock()

        val result = subject.isMoviesGenreTimestampOutdated(timestampDao)

        verify(timestampDao).getTimestamp(movieGenresTimestamp.id)
        assertTrue(result)
    }



    @Test
    fun isMoviesPageTimestampOutdated_retrievesDataFromDao_andVeirfiesUpdated() {
        val moviePageTimestamp = subject.createMoviePageTimestamp(1)
        val timestampDao: TimestampDao = mock()

        val result = subject.isMoviePageTimestampOutdated(timestampDao, 1)

        verify(timestampDao).getTimestamp(moviePageTimestamp.id, 1)
        assertTrue(result)
    }

    @Test
    fun isMovieCreditsTimestampOutdated_retrievesDataFromDao_andVeirfiesUpdated() {
        val creditTimestamp = subject.createMovieCreditTimestamp(1)
        val timestampDao: TimestampDao = mock()

        val result = subject.isMovieCreditsTimestampOutdated(timestampDao, 1)

        verify(timestampDao).getTimestamp(creditTimestamp.id, 1)
        assertTrue(result)
    }

}