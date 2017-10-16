package com.jpp.moviespreview.app.data.cache.configuration

import com.jpp.moviespreview.app.data.ImagesConfiguration
import com.jpp.moviespreview.app.data.MoviesConfiguration
import com.jpp.moviespreview.app.data.cache.configuration.MoviesConfigurationCacheDataMapper
import org.junit.Assert.assertEquals
import org.junit.Test

class MoviesConfigurationCacheDataMapperTest {


    @Test
    fun convertImagesConfigurationToCacheModel() {
        val posterSizes = arrayListOf("size1", "size2", "size3")
        val imagesConfiguration = ImagesConfiguration("baseUrl", posterSizes)
        val moviesConfiguration = MoviesConfiguration(imagesConfiguration)

        val dataImageSizes = MoviesConfigurationCacheDataMapper().convertImagesConfigurationToCacheModel(16L, moviesConfiguration)
        assertEquals(3, dataImageSizes.size)

        assertEquals(16L, dataImageSizes[0].imageConfig)
        assertEquals("size1", dataImageSizes[0].size)

        assertEquals(16L, dataImageSizes[1].imageConfig)
        assertEquals("size2", dataImageSizes[1].size)

        assertEquals(16L, dataImageSizes[2].imageConfig)
        assertEquals("size3", dataImageSizes[2].size)
    }

    @Test
    fun convertMoviesConfigurationToCacheModel() {
        val posterSizes = arrayListOf("size1", "size2", "size3")
        val imagesConfiguration = ImagesConfiguration("baseUrl", posterSizes)
        val moviesConfiguration = MoviesConfiguration(imagesConfiguration)

        val cacheImageConfig = MoviesConfigurationCacheDataMapper().convertMoviesConfigurationToCacheModel(moviesConfiguration)

        assertEquals("baseUrl", cacheImageConfig.baseUrl)
    }

}