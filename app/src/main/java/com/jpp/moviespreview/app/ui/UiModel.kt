package com.jpp.moviespreview.app.ui

import com.jpp.moviespreview.app.util.AllOpen

/**
 * Represents the configuration of the images from the UI perspective.
 * The URL that references the location of the images.
 * A list of possible sizes to retrieve (the presenters and views are the ones
 * that take care of finding the proper size)
 */
data class ImageConfiguration(private val baseUrl: String,
                              val size: String,
                              val realSize: Int?) {

    fun prepareImageUrl(path: String) = "$baseUrl$size$path"

}


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