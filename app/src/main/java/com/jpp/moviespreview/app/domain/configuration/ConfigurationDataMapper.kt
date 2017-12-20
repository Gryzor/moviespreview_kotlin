package com.jpp.moviespreview.app.domain.configuration

import com.jpp.moviespreview.app.domain.ImagesConfiguration
import com.jpp.moviespreview.app.domain.MoviesConfiguration
import com.jpp.moviespreview.app.data.ImagesConfiguration as DataImagesConfiguration
import com.jpp.moviespreview.app.data.MoviesConfiguration as DataMoviesConfiguration

/**
 * Maps configuration classes from the data layer to the domain layer
 *
 * Created by jpp on 10/5/17.
 */
class ConfigurationDataMapper {

    /**
     * Converts the MoviesConfiguration from data to model class.
     */
    fun convertMoviesConfigurationFromDataModel(dataMoviesConfiguration: DataMoviesConfiguration) = with(dataMoviesConfiguration) {
        MoviesConfiguration(convertImagesConfigurationFromDataModel(dataMoviesConfiguration.images))
    }

    private fun convertImagesConfigurationFromDataModel(imagesConfiguration: DataImagesConfiguration) = with(imagesConfiguration) {
        ImagesConfiguration(base_url, poster_sizes, profile_sizes)
    }

}