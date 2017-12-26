package com.jpp.moviespreview.app.ui

import com.jpp.moviespreview.app.util.AllOpen

/**
 * Represents the configuration of the images from the UI perspective.
 * [baseUrl] represents the base url where the images are located.
 * [size] represents the size of the image.
 */
interface ImageConfiguration {

    val baseUrl: String
    val size: String

    /**
     * Prepares and returns the prepared URL that represents this image configuration.
     */
    fun prepareImageUrl(path: String) = "$baseUrl$size$path"
}


/**
 * Represents the poster images configuration.
 */
data class PosterImageConfiguration(override val baseUrl: String,
                                    override val size: String) : ImageConfiguration

/**
 * Represents the profile images configurations.
 */
data class ProfileImageConfiguration(override val baseUrl: String,
                                     override val size: String) : ImageConfiguration


/**
 * Represents a Movie Genre
 */
data class MovieGenre(val id: Int,
                      val name: String,
                      val icon: Int)


/**
 * Represents a Movie for the UI module.
 */
@AllOpen
data class Movie(var id: Double,
                 var title: String,
                 var originalTitle: String,
                 var overview: String,
                 var releaseDate: String,
                 var originalLanguage: String,
                 val images: List<String>,
                 val genres: List<MovieGenre>,
                 val voteCount: Double,
                 val voteAverage: Float,
                 val popularity: Float) {

    // represents the image that is currently shown in the UI
    // it will be updated from the ViewPager in the UI
    var currentImageShown = 0

}


/**
 * Represents a page of Movies for the UI module.
 */
data class MoviePage(val page: Int,
                     val results: List<Movie>,
                     val totalPages: Int,
                     val totalResults: Int)