package com.jpp.moviespreview.app.ui

import com.jpp.moviespreview.app.domain.MoviesConfiguration as DomainMovieConfiguration

/**
 * Maps domain model to UI model
 * Created by jpp on 10/11/17.
 */

class DomainToUiDataMapper {

    fun convertConfigurationToImagesConfiguration(domainMoviesConfiguration: DomainMovieConfiguration) = with(domainMoviesConfiguration) {
        ImageConfiguration(imagesConfiguration.baseUrl, imagesConfiguration.sizes)
    }
}
