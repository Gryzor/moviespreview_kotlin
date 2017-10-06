package com.jpp.moviespreview.app.data.cache

import com.jpp.moviespreview.app.data.cache.db.*
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.util.concurrent.TimeUnit

class MoviesCacheImplTest {

    private lateinit var subject: MoviesCacheImpl
    private lateinit var mapper: CacheDataMapper
    private lateinit var database: MoviesDataBase
    private lateinit var timestampDao: TimestampDao

    @Before
    fun setUp() {
        mapper = mock(CacheDataMapper::class.java)
        database = mock(MoviesDataBase::class.java)
        subject = MoviesCacheImpl(mapper, database)

        timestampDao = mock(TimestampDao::class.java)
        `when`(database.timestampDao()).thenReturn(timestampDao)
    }

    @Test
    fun isLastConfigOlderThan_aDay_whenDifferenceIsTwoHours() {
        val timestamp = mock(Timestamp::class.java)
        `when`(timestampDao.getTimestamp(MoviesCacheImpl.MOVIES_CONFIGURATION_TIMESTAMP.id)).thenReturn(timestamp)
        `when`(timestamp.lastUpdate).thenReturn(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(2))
        val result = subject.isLastConfigOlderThan(TimeUnit.DAYS.toMillis(1))
        assertFalse(result)
    }

    @Test
    fun isLastConfigOlderThan_aDay_whenDifferenceIsAWeek() {
        val timestamp = mock(Timestamp::class.java)
        `when`(timestampDao.getTimestamp(MoviesCacheImpl.MOVIES_CONFIGURATION_TIMESTAMP.id)).thenReturn(timestamp)
        `when`(timestamp.lastUpdate).thenReturn(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7))
        val result = subject.isLastConfigOlderThan(TimeUnit.DAYS.toMillis(1))
        assertTrue(result)
    }

    @Test
    fun isLastConfigOlderThan_aDay_whenIsFirstTime() {
        `when`(timestampDao.getTimestamp(MoviesCacheImpl.MOVIES_CONFIGURATION_TIMESTAMP.id)).thenReturn(null)
        val result = subject.isLastConfigOlderThan(TimeUnit.DAYS.toMillis(1))
        assertTrue(result)
    }

}
