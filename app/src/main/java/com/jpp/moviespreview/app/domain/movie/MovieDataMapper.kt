package com.jpp.moviespreview.app.domain.movie

import com.jpp.moviespreview.app.domain.MoviePage
import com.jpp.moviespreview.app.domain.Movie
import com.jpp.moviespreview.app.domain.Genre
import com.jpp.moviespreview.app.data.MoviePage as DataMoviePage
import com.jpp.moviespreview.app.data.Movie as DataMovie

/**
 * Maps data objects into domain objects
 *
 * Created by jpp on 10/21/17.
 */

open class MovieDataMapper {


    /**
     * Converts a [DataMoviePage] into a domain [MoviePage]
     */
    fun convertDataMoviePageIntoDomainMoviePage(dataMoviePage: DataMoviePage, genres: List<Genre>) = with(dataMoviePage) {
        MoviePage(page, convertDataMoviesIntoDomainMovies(results, genres), total_pages, total_results)
    }


    /**
     * Converts a list of [DataMovie] into a list of domain [Movie]s
     */
    private fun convertDataMoviesIntoDomainMovies(dataMovies: List<DataMovie>, genres: List<Genre>): List<Movie> {
        return dataMovies.mapTo(ArrayList()) {
            Movie(it.id,
                    it.title,
                    it.original_title,
                    it.overview,
                    it.release_date,
                    it.original_language,
                    it.poster_path ?: "empty", // might be null, assign empty as default value
                    it.backdrop_path ?: "empty", // might be null, assign empty as default value
                    mapGenresIdToDomainGenres(it.genre_ids, genres),
                    it.vote_count,
                    it.vote_average,
                    it.popularity)
        }
    }


    /**
     * Maps a list of genre ids into the corresponding Genre list
     */
    fun mapGenresIdToDomainGenres(genreIds: List<Int>, genres: List<Genre>): List<Genre> {
        val result = ArrayList<Genre>()
        genres.filterTo(result) { genreIds.contains(it.id) }
        return result
    }


    /**
     * Converts a domain [Movie] into a [DataMovie]
     */
    fun convertDomainMovieIntoDataMovie(domainMovie: Movie) = with(domainMovie) {
        DataMovie(id,
                title,
                originalTitle,
                overview,
                releaseDate,
                originalLanguage,
                posterPath,
                backdropPath,
                genres.mapTo(arrayListOf()) { it.id },
                voteCount,
                voteAverage,
                popularity)
    }
}
