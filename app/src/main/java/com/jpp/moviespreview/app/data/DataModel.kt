package com.jpp.moviespreview.app.data

/**
 * Represents the configuration of the images tha the data layer can provide.
 */
data class ImagesConfiguration(var base_url: String,
                               var poster_sizes: List<String>,
                               var profile_sizes: List<String>)

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
data class Movie(var id: Double,
                 var title: String,
                 var original_title: String,
                 var overview: String,
                 var release_date: String,
                 var original_language: String,
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
data class CastCharacter(var cast_id: Double,
                         var character: String,
                         var credit_id: String,
                         var gender: Int,
                         var name: String,
                         var order: Int,
                         var profile_path: String = "empty")

/**
 * Represents a person that is part of a crew of a [Movie].
 */
data class CrewPerson(var credit_id: String,
                      var department: String,
                      var gender: Int,
                      var id: Double,
                      var job: String,
                      var name: String,
                      var profile_path: String = "empty")

/**
 * Represents the credits of a [Movie]
 */
data class MovieCredits(var id: Double,
                        var cast: List<CastCharacter>,
                        var crew: List<CrewPerson>)


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
data class MultiSearchResult(var id: Double,
                             var poster_path: String?,
                             var backdrop_path: String?,
                             var overview: String,
                             var release_date: String?,
                             var original_title: String?,
                             var genre_ids: List<Int>?,
                             var media_type: String,
                             var original_language: String?,
                             var vote_count: Double?,
                             var vote_average: Float?,
                             var popularity: Float?,
                             var name: String?,
                             var known_for: List<Movie>?)
