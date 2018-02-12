package com.jpp.moviespreview.app.domain.configuration

import com.jpp.moviespreview.app.domain.ImageConfiguration
import com.jpp.moviespreview.app.domain.ImageConfiguration.Companion.POSTER
import com.jpp.moviespreview.app.domain.ImageConfiguration.Companion.PROFILE
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


    private fun convertImagesConfigurationFromDataModel(imagesConfiguration: DataImagesConfiguration): List<ImageConfiguration> =
            with(imagesConfiguration) {
                //map poster sizes
                poster_sizes.mapTo(ArrayList()) {
                    ImageConfiguration(base_url, it, POSTER)
                }.let {
                    // map profile pictures
                    profile_sizes.mapTo(it) {
                        ImageConfiguration(base_url, it, PROFILE)
                    }
                }
            }

}