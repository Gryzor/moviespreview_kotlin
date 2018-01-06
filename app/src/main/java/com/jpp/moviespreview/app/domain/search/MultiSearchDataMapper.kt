package com.jpp.moviespreview.app.domain.search

import com.jpp.moviespreview.app.domain.MultiSearchPage
import com.jpp.moviespreview.app.domain.MultiSearchResult
import com.jpp.moviespreview.app.domain.MultiSearchResult.Companion.MOVIE
import com.jpp.moviespreview.app.domain.MultiSearchResult.Companion.PERSON
import com.jpp.moviespreview.app.domain.MultiSearchResult.Companion.TV
import com.jpp.moviespreview.app.domain.MultiSearchResult.Companion.UNKNOWN
import com.jpp.moviespreview.app.data.Movie as DataMovie
import com.jpp.moviespreview.app.data.MultiSearchPage as DataResultPage
import com.jpp.moviespreview.app.data.MultiSearchResult as DataResult

/**
 * Maps data objects into domain objects
 *
 * Created by jpp on 1/6/18.
 */
class MultiSearchDataMapper {


    /**
     * Converts a [DataResultPage] into a domain [MultiSearchPage]
     */
    fun convertDataSearchPageIntoDomainSearchResult(dataResultPage: DataResultPage) = with(dataResultPage) {
        MultiSearchPage(page, convertDataSearchResultsIntoDomainSearchResults(results), total_pages, total_results)
    }


    /**
     * Converts a list of [DataResult] into a list of domain [MultiSearchResult]
     */
    private fun convertDataSearchResultsIntoDomainSearchResults(dataSearchResults: List<DataResult>): List<MultiSearchResult> {
        return dataSearchResults.mapTo(ArrayList()) {
            MultiSearchResult(it.id,
                    it.poster_path,
                    mapMediaType(it.media_type),
                    it.name,
                    it.title)
        }
    }

    private fun mapMediaType(mediaType: String): Long = when (mediaType) {
        "movie" -> MOVIE
        "tv" -> TV
        "person" -> PERSON
        else -> UNKNOWN
    }

}