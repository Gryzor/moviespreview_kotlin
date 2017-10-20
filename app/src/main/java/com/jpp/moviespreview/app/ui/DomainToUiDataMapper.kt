package com.jpp.moviespreview.app.ui

import com.jpp.moviespreview.app.domain.MoviesConfiguration as DomainMovieConfiguration
import com.jpp.moviespreview.app.domain.Genre as DomainGenre

/**
 * Maps domain model to UI model
 * Created by jpp on 10/11/17.
 */

class DomainToUiDataMapper {

    fun convertConfigurationToImagesConfiguration(domainMoviesConfiguration: DomainMovieConfiguration) = with(domainMoviesConfiguration) {
        ImageConfiguration(imagesConfiguration.baseUrl, imagesConfiguration.sizes)
    }


    fun convertDomainGenres(domainGenres: List<DomainGenre>): List<MovieGenre> {
        return domainGenres.mapTo(ArrayList()) {
            MovieGenre(it.id, it.name)
        }
    }

}
