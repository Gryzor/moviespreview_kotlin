package com.jpp.moviespreview.app.data.db

import com.jpp.moviespreview.app.data.cache.configuration.MoviesConfigurationCacheDataMapper
import com.jpp.moviespreview.app.data.cache.configuration.MoviesConfigurationCacheImpl
import com.jpp.moviespreview.app.data.cache.db.*
import com.jpp.moviespreview.app.util.TimeUtils
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import java.util.concurrent.TimeUnit

/**
 * Created by jpp on 10/17/17.
 */
class TimestampDaoTest {

    private lateinit var database: MoviesDataBase
    private lateinit var timestampDao: TimestampDao

    private var timeUtils = TimeUtils()

    @Before
    fun setUp() {
        database = Mockito.mock(MoviesDataBase::class.java)

        timestampDao = Mockito.mock(TimestampDao::class.java)
        Mockito.`when`(database.timestampDao()).thenReturn(timestampDao)
    }

    @Test
    fun isLastConfigOlderThan_aDay_whenDifferenceIsTwoHours() {
        val timestamp = Mockito.mock(Timestamp::class.java)
        Mockito.`when`(timestampDao.getTimestamp(MoviesConfigurationCacheImpl.MOVIES_CONFIGURATION_TIMESTAMP.id)).thenReturn(timestamp)
        Mockito.`when`(timestamp.lastUpdate).thenReturn(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(2))
        val result = database.timestampDao().isTimestampOlderThan(MoviesConfigurationCacheImpl.MOVIES_CONFIGURATION_TIMESTAMP, TimeUnit.DAYS.toMillis(1), timeUtils)
        Assert.assertFalse(result)
    }

    @Test
    fun isLastConfigOlderThan_aDay_whenDifferenceIsAWeek() {
        val timestamp = Mockito.mock(Timestamp::class.java)
        Mockito.`when`(timestampDao.getTimestamp(MoviesConfigurationCacheImpl.MOVIES_CONFIGURATION_TIMESTAMP.id)).thenReturn(timestamp)
        Mockito.`when`(timestamp.lastUpdate).thenReturn(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7))
        val result = database.timestampDao().isTimestampOlderThan(MoviesConfigurationCacheImpl.MOVIES_CONFIGURATION_TIMESTAMP, TimeUnit.DAYS.toMillis(1), timeUtils)
        Assert.assertTrue(result)
    }

    @Test
    fun isLastConfigOlderThan_aDay_whenIsFirstTime() {
        Mockito.`when`(timestampDao.getTimestamp(MoviesConfigurationCacheImpl.MOVIES_CONFIGURATION_TIMESTAMP.id)).thenReturn(null)
        val result = database.timestampDao().isTimestampOlderThan(MoviesConfigurationCacheImpl.MOVIES_CONFIGURATION_TIMESTAMP, TimeUnit.DAYS.toMillis(1), timeUtils)
        Assert.assertTrue(result)
    }
}