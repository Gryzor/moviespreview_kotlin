package com.jpp.moviespreview.app.data.cache

import com.jpp.moviespreview.app.data.cache.db.*
import com.jpp.moviespreview.app.extentions.TimeUtils
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import java.util.concurrent.TimeUnit

class MoviesCacheImplTest {

    private lateinit var subject: MoviesCacheImpl
    private lateinit var mapper: CacheDataMapper
    private lateinit var database: MoviesDataBase
    private lateinit var timestampDao: TimestampDao
    private lateinit var imageConfigDao: ImageConfigDao

    private var timeUtils = TimeUtils()

    @Before
    fun setUp() {
        mapper = mock(CacheDataMapper::class.java)
        database = mock(MoviesDataBase::class.java)
        subject = MoviesCacheImpl(mapper, database)

        timestampDao = mock(TimestampDao::class.java)
        `when`(database.timestampDao()).thenReturn(timestampDao)

        imageConfigDao = mock(ImageConfigDao::class.java)
        `when`(database.imageConfigDao()).thenReturn(imageConfigDao)
    }

    @Test
    fun isLastConfigOlderThan_aDay_whenDifferenceIsTwoHours() {
        val timestamp = mock(Timestamp::class.java)
        `when`(timestampDao.getTimestamp(MoviesCacheImpl.MOVIES_CONFIGURATION_TIMESTAMP.id)).thenReturn(timestamp)
        `when`(timestamp.lastUpdate).thenReturn(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(2))
        val result = subject.isLastConfigOlderThan(TimeUnit.DAYS.toMillis(1), timeUtils)
        assertFalse(result)
    }

    @Test
    fun isLastConfigOlderThan_aDay_whenDifferenceIsAWeek() {
        val timestamp = mock(Timestamp::class.java)
        `when`(timestampDao.getTimestamp(MoviesCacheImpl.MOVIES_CONFIGURATION_TIMESTAMP.id)).thenReturn(timestamp)
        `when`(timestamp.lastUpdate).thenReturn(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7))
        val result = subject.isLastConfigOlderThan(TimeUnit.DAYS.toMillis(1), timeUtils)
        assertTrue(result)
    }

    @Test
    fun isLastConfigOlderThan_aDay_whenIsFirstTime() {
        `when`(timestampDao.getTimestamp(MoviesCacheImpl.MOVIES_CONFIGURATION_TIMESTAMP.id)).thenReturn(null)
        val result = subject.isLastConfigOlderThan(TimeUnit.DAYS.toMillis(1), timeUtils)
        assertTrue(result)
    }

    @Test
    fun getLastMovieConfiguration_whenLastImageConfigIsNull_returnsNull() {
        `when`(imageConfigDao.getLastImageConfig()).thenReturn(null)
        val lastImageConfig = subject.getLastMovieConfiguration()
        assertNull(lastImageConfig)
    }

    @Test(expected = NullPointerException::class)
    fun getLastMovieConfiguration_whenImageSizesIsNull_throwsException() {
        val lastImageConfig = mock(ImageConfig::class.java)
        `when`(imageConfigDao.getLastImageConfig()).thenReturn(lastImageConfig)
        val id = 12L
        `when`(lastImageConfig.id).thenReturn(id)
        `when`(imageConfigDao.getImageSizesForConfig(id)).thenReturn(null)
        subject.getLastMovieConfiguration()
    }

    @Test
    fun getLastMovieConfiguration_returnsLastConfig() {
        val lastImageConfig = mock(ImageConfig::class.java)
        `when`(imageConfigDao.getLastImageConfig()).thenReturn(lastImageConfig)
        val id = 12L
        `when`(lastImageConfig.id).thenReturn(id)

        val imagesConfig = ArrayList<ImageSize>()
        imagesConfig.add(mock(ImageSize::class.java))
        imagesConfig.add(mock(ImageSize::class.java))
        imagesConfig.add(mock(ImageSize::class.java))
        `when`(imageConfigDao.getImageSizesForConfig(id)).thenReturn(imagesConfig)

        subject.getLastMovieConfiguration()

        verify(mapper).convertCacheImageConfigurationToDataMoviesConfiguration(lastImageConfig, imagesConfig)
    }
}
