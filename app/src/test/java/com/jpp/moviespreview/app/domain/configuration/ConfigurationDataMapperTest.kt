package com.jpp.moviespreview.app.domain.configuration

import com.jpp.moviespreview.app.domain.ImageConfiguration.Companion.POSTER
import com.jpp.moviespreview.app.domain.ImageConfiguration.Companion.PROFILE
import org.junit.Assert.assertEquals
import org.junit.Test
import com.jpp.moviespreview.app.data.ImagesConfiguration as DataImagesConfiguration
import com.jpp.moviespreview.app.data.MoviesConfiguration as DataMoviesConfiguration


class ConfigurationDataMapperTest {


    @Test
    fun convertMoviesConfigurationFromDataModel() {
        val posterSizes = arrayListOf("size1", "size2", "size3")
        val profileSizes = arrayListOf("size1", "size2", "size3", "size4")
        val dataImagesConfiguration = DataImagesConfiguration("baseUrl", posterSizes, profileSizes)
        val dataMoviesConfiguration = DataMoviesConfiguration(dataImagesConfiguration)

        val domainMoviesConfiguration = ConfigurationDataMapper().convertMoviesConfigurationFromDataModel(dataMoviesConfiguration)

        assertEquals(3, domainMoviesConfiguration.imagesConfiguration.count { it.type == POSTER })
        assertEquals(4, domainMoviesConfiguration.imagesConfiguration.count { it.type == PROFILE })
        assertEquals(7, domainMoviesConfiguration.imagesConfiguration.count { it.baseUrl == "baseUrl" })
    }

}
