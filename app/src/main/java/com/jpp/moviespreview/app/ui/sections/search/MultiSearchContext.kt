package com.jpp.moviespreview.app.ui.sections.search

import com.jpp.moviespreview.app.domain.MultiSearchParam
import com.jpp.moviespreview.app.ui.Movie
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.MultiSearchPage
import com.jpp.moviespreview.app.ui.MultiSearchResult

/**
 * Multi search is a complex feature. This is a particular context, that will be specific
 * for multi search feature and will survive to activity rotation.
 *
 * Created by jpp on 1/10/18.
 */
class MultiSearchContext(private val context: MoviesContext) {

    var onGoingQueryParam: MultiSearchParam? = null
    private var searchPages = ArrayList<MultiSearchPage>()

    fun getPosterImageConfigs() = context.posterImageConfig

    fun getProfileImageConfig() = context.profileImageConfig

    fun addSearchPage(multiSearchPage: MultiSearchPage) {
        if (searchPages.contains(multiSearchPage)) {
            throw IllegalStateException("Wrong! Your're trying to add an existing page")
        }
        searchPages.add(multiSearchPage)
    }

    fun getUIMovieGenres() = context.movieGenres

    fun clearPages() = searchPages.clear()

    fun hasSearchPages() = searchPages.size > 0

    fun getAllSearchPages(): List<MultiSearchPage> = searchPages

    fun setSelectedMovie(movie: Movie) {
        context.selectedMovie = movie
    }

    fun getAllSearchResults(): List<MultiSearchResult> {
        val result = ArrayList<MultiSearchResult>()
        for (resultPage in searchPages) {
            result.addAll(resultPage.results)
        }
        return result
    }
}