package com.jpp.moviespreview.app.data.cache.configuration

import com.jpp.moviespreview.app.data.cache.db.*
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class MoviesConfigurationCacheImplTest {

    private lateinit var subject: MoviesConfigurationCacheImpl
    private lateinit var mapper: MoviesConfigurationCacheDataMapper
    private lateinit var database: MoviesDataBase
    private lateinit var timestampDao: TimestampDao
    private lateinit var imageConfigDao: ImageConfigDao

    @Before
    fun setUp() {
        mapper = mock(MoviesConfigurationCacheDataMapper::class.java)
        database = mock(MoviesDataBase::class.java)
        subject = MoviesConfigurationCacheImpl(mapper, database)

        timestampDao = mock(TimestampDao::class.java)
        `when`(database.timestampDao()).thenReturn(timestampDao)

        imageConfigDao = mock(ImageConfigDao::class.java)
        `when`(database.imageConfigDao()).thenReturn(imageConfigDao)
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
