package com.jpp.moviespreview.app.domain.configuration

import org.junit.Assert.assertEquals
import org.junit.Test
import com.jpp.moviespreview.app.data.ImagesConfiguration as DataImagesConfiguration
import com.jpp.moviespreview.app.data.MoviesConfiguration as DataMoviesConfiguration


class ConfigurationDataMapperTest {


    @Test
    fun convertMoviesConfigurationFromDataModel() {
        val posterSizes = arrayListOf("size1", "size2", "size3")
        val dataImagesConfiguration = DataImagesConfiguration("baseUrl", posterSizes)
        val dataMoviesConfiguration = DataMoviesConfiguration(dataImagesConfiguration)

        val domainMoviesConfiguration = ConfigurationDataMapper().convertMoviesConfigurationFromDataModel(dataMoviesConfiguration)

        assertEquals(3, domainMoviesConfiguration.imagesConfiguration.sizes.size)
        assertEquals("baseUrl", domainMoviesConfiguration.imagesConfiguration.baseUrl)
    }

}
