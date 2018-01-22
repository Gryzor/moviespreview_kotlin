package com.jpp.moviespreview.app.data

/**
 * Represents the configuration of the images tha the data layer can provide.
 */
data class ImagesConfiguration(val base_url: String,
                               val poster_sizes: List<String>,
                               val profile_sizes: List<String>)

/**
 * Represents the general configuration of the movies.
 */
data class MoviesConfiguration(val images: ImagesConfiguration)


/**
 * Represents a Genre.
 */
data class Genre(val id: Int,
                 val name: String)

/**
 * Represents all the Genres available.
 */
data class Genres(val genres: List<Genre>)

/**
 * Represents a Movie retrieved from the backend.
 */
data class Movie(val id: Double,
                 val title: String,
                 val original_title: String,
                 val overview: String,
                 val release_date: String,
                 val original_language: String,
                 val poster_path: String?,
                 val backdrop_path: String?,
                 val genre_ids: List<Int>,
                 val vote_count: Double,
                 val vote_average: Float,
                 val popularity: Float)

/**
 * Represents a page of Movies retrieved from the backend.
 */
data class MoviePage(val page: Int,
                     val results: List<Movie>,
                     val total_pages: Int,
                     val total_results: Int)

/**
 * Represents a character that is present in the cast of a [Movie].
 */
data class CastCharacter(val cast_id: Double,
                         val character: String,
                         val credit_id: String,
                         val gender: Int,
                         val name: String,
                         val order: Int,
                         val profile_path: String = "empty")

/**
 * Represents a person that is part of a crew of a [Movie].
 */
data class CrewPerson(val credit_id: String,
                      val department: String,
                      val gender: Int,
                      val id: Double,
                      val job: String,
                      val name: String,
                      val profile_path: String = "empty")

/**
 * Represents the credits of a [Movie]
 */
data class MovieCredits(val id: Double,
                        val cast: List<CastCharacter>,
                        val crew: List<CrewPerson>)


/**
 * Represents a page of results of a search retrieved from the backend.
 */
data class MultiSearchPage(val page: Int,
                           val results: List<MultiSearchResult>,
                           val total_pages: Int,
                           val total_results: Int)

/**
 * Represents an item int the result of a multi search
 */
data class MultiSearchResult(val id: Double,
                             val poster_path: String?,
                             val profile_path: String?,
                             val media_type: String,
                             val name: String?,
                             val title: String?,
                             val original_title: String?,
                             val overview: String?,
                             val release_date: String?,
                             val original_language: String?,
                             val backdrop_path: String?,
                             val genre_ids: List<Int>?,
                             val vote_count: Double?,
                             val vote_average: Float?,
                             val popularity: Float?)

/**
 * Represents a License description for the
 * libraries used by the application.
 */
data class License(val id: Int,
                   val name: String,
                   val url: String)

/**
 * Represents the list of all [License] used by the application.
 */
data class Licenses(val licenses: List<License>)

