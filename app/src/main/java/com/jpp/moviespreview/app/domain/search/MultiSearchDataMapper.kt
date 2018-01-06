package com.jpp.moviespreview.app.domain.search

import com.jpp.moviespreview.app.data.Movie as DataMovie
import com.jpp.moviespreview.app.domain.Movie
import com.jpp.moviespreview.app.domain.Genre
import com.jpp.moviespreview.app.domain.MultiSearchPage
import com.jpp.moviespreview.app.domain.MultiSearchResult
import com.jpp.moviespreview.app.domain.movie.MovieDataMapper
import com.jpp.moviespreview.app.data.MultiSearchPage as DataResultPage
import com.jpp.moviespreview.app.data.MultiSearchResult as DataResult
import com.jpp.moviespreview.app.domain.MultiSearchResult.Companion.MOVIE
import com.jpp.moviespreview.app.domain.MultiSearchResult.Companion.TV
import com.jpp.moviespreview.app.domain.MultiSearchResult.Companion.PERSON

/**
 * Maps data objects into domain objects
 *
 * Created by jpp on 1/6/18.
 */
class MultiSearchDataMapper(private val movieDataMapper: MovieDataMapper) {


    /**
     * Converts a [DataResultPage] into a domain [MultiSearchPage]
     */
    fun convertDataSearchPageIntoDomainSeatchResult(dataResultPage: DataResultPage, genres: List<Genre>) = with(dataResultPage) {
        MultiSearchPage(page, convertDataSearchResultsIntoDomainSearchResults(results, genres), total_pages, total_results)
    }


    /**
     * Converts a list of [DataResult] into a list of domain [MultiSearchResult]
     */
    private fun convertDataSearchResultsIntoDomainSearchResults(dataSearchResults: List<DataResult>,
                                                                genres: List<Genre>): List<MultiSearchResult> {
        return dataSearchResults.mapTo(ArrayList()) {
            MultiSearchResult(it.id,
                    it.poster_path,
                    it.backdrop_path,
                    it.overview,
                    it.release_date,
                    it.original_title,
                    mapGenresIdToDomainGenres(it.genre_ids, genres),
                    mapMediaType(it.media_type),
                    it.original_language,
                    it.vote_count,
                    it.vote_average,
                    it.popularity,
                    it.name,
                    convertDataMoviesIntoDomainMovies(it.known_for, genres))
        }
    }


    /**
     * Maps a list of genre ids into the corresponding Genre list
     */
    private fun mapGenresIdToDomainGenres(genreIds: List<Int>?, genres: List<Genre>): List<Genre>? {
        return if (genreIds != null) {
            movieDataMapper.mapGenresIdToDomainGenres(genreIds, genres)
        } else {
            null
        }
    }

    /**
     * Converts a list of [DataMovie] into a list of domain [Movie]s
     */
    private fun convertDataMoviesIntoDomainMovies(dataMovies: List<DataMovie>?, genres: List<Genre>): List<Movie>? {
        return if (dataMovies != null) {
            movieDataMapper.convertDataMoviesIntoDomainMovies(dataMovies, genres)
        } else {
            return null
        }
    }

    private fun mapMediaType(mediaType: String): Long = when (mediaType) {
        "movie" -> MOVIE
        "tv" -> TV
        "person" -> PERSON
        else -> throw IllegalArgumentException("Invalid mediaType [$mediaType]")
    }

}