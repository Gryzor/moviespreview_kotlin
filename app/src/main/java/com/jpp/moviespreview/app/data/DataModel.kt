package com.jpp.moviespreview.app.data

/**
 * Represents the configuration of the images tha the data layer can provide.
 */
data class ImagesConfiguration(var base_url: String,
                               var poster_sizes: List<String>)

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
