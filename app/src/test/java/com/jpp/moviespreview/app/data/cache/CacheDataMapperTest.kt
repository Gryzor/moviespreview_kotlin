package com.jpp.moviespreview.app.data.cache

import com.jpp.moviespreview.app.data.ImagesConfiguration
import com.jpp.moviespreview.app.data.MoviesConfiguration
import org.junit.Assert.assertEquals
import org.junit.Test

class CacheDataMapperTest {


    @Test
    fun convertImagesConfigurationToCacheModel() {
        val posterSizes = arrayListOf("size1", "size2", "size3")
        val imagesConfiguration = ImagesConfiguration("baseUrl", posterSizes)
        val moviesConfiguration = MoviesConfiguration(imagesConfiguration)

        val dataImageSizes = CacheDataMapper().convertImagesConfigurationToCacheModel(16L, moviesConfiguration)
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

        val cacheImageConfig = CacheDataMapper().convertMoviesConfigurationToCacheModel(moviesConfiguration)

        assertEquals("baseUrl", cacheImageConfig.baseUrl)
    }

}