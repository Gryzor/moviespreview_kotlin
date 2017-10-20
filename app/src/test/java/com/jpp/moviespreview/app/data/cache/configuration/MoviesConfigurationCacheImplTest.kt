package com.jpp.moviespreview.app.data.cache.configuration

import com.jpp.moviespreview.app.data.MoviesConfiguration
import com.jpp.moviespreview.app.data.cache.CacheTimestampUtils
import com.jpp.moviespreview.app.data.cache.db.*
import com.jpp.moviespreview.app.mock
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
    private lateinit var cacheTimestampUtils: CacheTimestampUtils

    @Before
    fun setUp() {
        mapper = mock()
        database = mock()
        cacheTimestampUtils = mock()
        subject = MoviesConfigurationCacheImpl(mapper, database, cacheTimestampUtils)

        timestampDao = mock()
        `when`(database.timestampDao()).thenReturn(timestampDao)

        imageConfigDao = mock()
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
        val lastImageConfig: ImageConfig = mock()
        `when`(imageConfigDao.getLastImageConfig()).thenReturn(lastImageConfig)
        val id = 12L
        `when`(lastImageConfig.id).thenReturn(id)
        `when`(imageConfigDao.getImageSizesForConfig(id)).thenReturn(null)
        subject.getLastMovieConfiguration()
    }

    @Test
    fun getLastMovieConfiguration_returnsLastConfig() {
        val lastImageConfig: ImageConfig = mock()
        `when`(imageConfigDao.getLastImageConfig()).thenReturn(lastImageConfig)
        val id = 12L
        `when`(lastImageConfig.id).thenReturn(id)

        val imagesConfig = ArrayList<ImageSize>()
        imagesConfig.add(mock())
        imagesConfig.add(mock())
        imagesConfig.add(mock())
        `when`(imageConfigDao.getImageSizesForConfig(id)).thenReturn(imagesConfig)

        subject.getLastMovieConfiguration()

        verify(mapper).convertCacheImageConfigurationToDataMoviesConfiguration(lastImageConfig, imagesConfig)
    }


    @Test
    fun saveMoviesConfig_insertsTimestamp_thenInsertsImageConfg_andSizes() {
        val moviesConfig: MoviesConfiguration = mock()
        val currentTimestamp: Timestamp = mock()
        `when`(cacheTimestampUtils.createMoviesConfigurationTimestamp()).thenReturn(currentTimestamp)

        subject.saveMoviesConfig(moviesConfig)

        verify(timestampDao).insertTimestamp(currentTimestamp)
    }


    @Test
    fun saveMoviesConfig_insertsImageConfg_andSizes() {
        val moviesConfig: MoviesConfiguration = mock()
        val cacheMovieConfig: ImageConfig = mock()
        `when`(mapper.convertMoviesConfigurationToCacheModel(moviesConfig)).thenReturn(cacheMovieConfig)

        val parentId = 12L
        `when`(imageConfigDao.insertImageConfig(cacheMovieConfig)).thenReturn(parentId)

        val cacheImageSizes = ArrayList<ImageSize>()
        `when`(mapper.convertImagesConfigurationToCacheModel(parentId, moviesConfig)).thenReturn(cacheImageSizes)

        subject.saveMoviesConfig(moviesConfig)

        verify(imageConfigDao).insertImageConfig(cacheMovieConfig)
        verify(imageConfigDao).insertAllImageSize(cacheImageSizes)
    }
}
