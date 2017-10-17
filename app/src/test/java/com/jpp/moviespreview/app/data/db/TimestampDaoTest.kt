package com.jpp.moviespreview.app.data.db

import com.jpp.moviespreview.app.data.cache.db.MoviesDataBase
import com.jpp.moviespreview.app.data.cache.db.Timestamp
import com.jpp.moviespreview.app.data.cache.db.TimestampDao
import com.jpp.moviespreview.app.data.cache.db.isTimestampOlderThan
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

    companion object {
        val TIMESTAMP = Timestamp(12)
    }

    @Before
    fun setUp() {
        database = Mockito.mock(MoviesDataBase::class.java)

        timestampDao = Mockito.mock(TimestampDao::class.java)
        Mockito.`when`(database.timestampDao()).thenReturn(timestampDao)
    }

    @Test
    fun isLastConfigOlderThan_aDay_whenDifferenceIsTwoHours() {
        val timestamp = Mockito.mock(Timestamp::class.java)
        Mockito.`when`(timestampDao.getTimestamp(TIMESTAMP.id)).thenReturn(timestamp)
        Mockito.`when`(timestamp.lastUpdate).thenReturn(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(2))
        val result = database.timestampDao().isTimestampOlderThan(TIMESTAMP, TimeUnit.DAYS.toMillis(1), timeUtils)
        Assert.assertFalse(result)
    }

    @Test
    fun isLastConfigOlderThan_aDay_whenDifferenceIsAWeek() {
        val timestamp = Mockito.mock(Timestamp::class.java)
        Mockito.`when`(timestampDao.getTimestamp(TIMESTAMP.id)).thenReturn(timestamp)
        Mockito.`when`(timestamp.lastUpdate).thenReturn(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7))
        val result = database.timestampDao().isTimestampOlderThan(TIMESTAMP, TimeUnit.DAYS.toMillis(1), timeUtils)
        Assert.assertTrue(result)
    }

    @Test
    fun isLastConfigOlderThan_aDay_whenIsFirstTime() {
        Mockito.`when`(timestampDao.getTimestamp(TIMESTAMP.id)).thenReturn(null)
        val result = database.timestampDao().isTimestampOlderThan(TIMESTAMP, TimeUnit.DAYS.toMillis(1), timeUtils)
        Assert.assertTrue(result)
    }
}