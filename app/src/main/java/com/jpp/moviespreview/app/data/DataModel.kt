package com.jpp.moviespreview.app.data

/**
 * Represents the configuration of the images tha the data layer can provide.
 */
data class ImagesConfiguration(var base_url: String,
                               var poster_sizes: List<String>)

/**
 * Represents the general configuration of the movies
 */
data class MoviesConfiguration(val images: ImagesConfiguration)
