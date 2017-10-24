package com.jpp.moviespreview.app.domain

/**
 * Represents the configuration of the images tha the data layer can provide.
 */
data class ImagesConfiguration(val baseUrl: String,
                               val sizes: List<String>)

/**
 * Represents the general configuration of the movies
 */
data class MoviesConfiguration(val imagesConfiguration: ImagesConfiguration)


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