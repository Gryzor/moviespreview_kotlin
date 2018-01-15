package com.jpp.moviespreview.app.domain.search

import com.jpp.moviespreview.app.domain.Genre
import com.jpp.moviespreview.app.domain.MultiSearchPage
import com.jpp.moviespreview.app.domain.MultiSearchResult
import com.jpp.moviespreview.app.domain.MultiSearchResult.Companion.MOVIE
import com.jpp.moviespreview.app.domain.MultiSearchResult.Companion.PERSON
import com.jpp.moviespreview.app.domain.MultiSearchResult.Companion.TV
import com.jpp.moviespreview.app.domain.MultiSearchResult.Companion.UNKNOWN
import com.jpp.moviespreview.app.domain.movie.MovieDataMapper
import com.jpp.moviespreview.app.data.Movie as DataMovie
import com.jpp.moviespreview.app.data.MultiSearchPage as DataResultPage
import com.jpp.moviespreview.app.data.MultiSearchResult as DataResult

/**
 * Maps data objects into domain objects
 *
 * Created by jpp on 1/6/18.
 */
class MultiSearchDataMapper(private val movieDataMapper: MovieDataMapper) {


    /**
     * Converts a [DataResultPage] into a domain [MultiSearchPage]
     */
    fun convertDataSearchPageIntoDomainSearchResult(dataResultPage: DataResultPage, query: String, genres: List<Genre>) = with(dataResultPage) {
        MultiSearchPage(page, convertDataSearchResultsIntoDomainSearchResults(results, genres), total_pages, total_results, query)
    }


    /**
     * Converts a list of [DataResult] into a list of domain [MultiSearchResult]
     */
    private fun convertDataSearchResultsIntoDomainSearchResults(dataSearchResults: List<DataResult>, genres: List<Genre>): List<MultiSearchResult> {
        return dataSearchResults.mapTo(ArrayList()) {
            MultiSearchResult(it.id,
                    it.poster_path,
                    it.profile_path,
                    mapMediaType(it.media_type),
                    it.name,
                    it.title,
                    it.original_title,
                    it.overview,
                    it.release_date,
                    it.original_language,
                    it.backdrop_path,
                    mapGenresIdToDomainGenres(it.genre_ids, genres),
                    it.vote_count,
                    it.vote_average,
                    it.popularity)
        }
    }

    fun mapGenresIdToDomainGenres(genreIds: List<Int>?, genres: List<Genre>): List<Genre>? {
        return if (genreIds != null) {
            movieDataMapper.mapGenresIdToDomainGenres(genreIds, genres)
        } else {
            null
        }
    }

    private fun mapMediaType(mediaType: String): Long = when (mediaType) {
        "movie" -> MOVIE
        "tv" -> TV
        "person" -> PERSON
        else -> UNKNOWN
    }

}