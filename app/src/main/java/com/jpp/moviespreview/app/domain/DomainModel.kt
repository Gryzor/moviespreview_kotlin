package com.jpp.moviespreview.app.domain

import android.support.annotation.IntDef

/**
 * Represents the configuration of the images from the Domain perspective.
 * [baseUrl] is the base url where the images are located.
 * [size] represents the size for this image config.
 * [type] maps the ImageConfiguration to a given type.
 */
data class ImageConfiguration(val baseUrl: String,
                              val size: String,
                              @Type val type: Long) {

    companion object {

        @IntDef(POSTER, PROFILE)
        @Retention(AnnotationRetention.SOURCE)
        annotation class Type

        const val POSTER = 0L
        const val PROFILE = 1L
    }
}

/**
 * Represents the general configuration of the movies
 */
data class MoviesConfiguration(val imagesConfiguration: List<ImageConfiguration>)


/**
 * Represents a Genre
 */
data class Genre(val id: Int,
                 val name: String)

/**
 * Represents a Movie for the domain module.
 *
 * IMPORTANT ABOUT THE DATE: the date is not transformed in the application, it's shown as it
 * gets from the API since the idea is that the API returns the date formatted according to
 * the provided locale.
 */
data class Movie(val id: Double,
                 val title: String,
                 val originalTitle: String,
                 val overview: String,
                 val releaseDate: String,
                 val originalLanguage: String,
                 val posterPath: String,
                 val backdropPath: String,
                 val genres: List<Genre>,
                 val voteCount: Double,
                 val voteAverage: Float,
                 val popularity: Float)


/**
 * Represents a page of Movies for the domain module.
 */
data class MoviePage(val page: Int,
                     val results: List<Movie>,
                     val totalPages: Int,
                     val totalResults: Int)


/**
 * Represents the input received by the [RetrieveMoviesInTheaterUseCase]
 */
data class MoviesInTheaterInputParam(val page: Int,
                                     val genres: List<Genre>)


/**
 * Represents a character that is present in the cast of a [Movie].
 */
data class CastCharacter(var castId: Double,
                         var character: String,
                         var creditId: String,
                         var gender: Int,
                         var name: String,
                         var order: Int,
                         var profilePath: String?)

/**
 * Represents a person that is part of a crew of a [Movie].
 */
data class CrewPerson(var creditId: String,
                      var department: String,
                      var gender: Int,
                      var id: Double,
                      var job: String,
                      var name: String,
                      var profilePath: String?)

/**
 * Represents the credits of a [Movie]
 */
data class MovieCredits(var id: Double,
                        var cast: List<CastCharacter>,
                        var crew: List<CrewPerson>)