package com.jpp.moviespreview.app.ui.sections.search

import com.jpp.moviespreview.app.domain.MultiSearchParam
import com.jpp.moviespreview.app.ui.Movie
import com.jpp.moviespreview.app.ui.MoviesContextHandler
import com.jpp.moviespreview.app.ui.MultiSearchPage
import com.jpp.moviespreview.app.ui.MultiSearchResult

/**
 * Multi searchFirstPage is a complex feature. This is a particular context, that will be specific
 * for multi searchFirstPage feature and will survive to activity rotation.
 *
 * Created by jpp on 1/10/18.
 */
class MultiSearchContext(private val moviesContextHandler: MoviesContextHandler) {

    private var searchPages = ArrayList<MultiSearchPage>()

    fun getPosterImageConfigs() = moviesContextHandler.getPosterImageConfigs()

    fun getProfileImageConfig() = moviesContextHandler.getProfileImageConfigs()

    fun addSearchPage(multiSearchPage: MultiSearchPage) {
        if (searchPages.contains(multiSearchPage)) {
            throw IllegalStateException("Wrong! Your're trying to add an existing page")
        }
        searchPages.add(multiSearchPage)
    }

    fun getUIMovieGenres() = moviesContextHandler.getMovieGenres()

    fun clearPages() = searchPages.clear()

    fun hasSearchPages() = searchPages.size > 0

    fun setSelectedMovie(movie: Movie) {
        moviesContextHandler.setSelectedMovie(movie)
    }

    fun getAllSearchResults(): List<MultiSearchResult> {
        val result = ArrayList<MultiSearchResult>()
        for (resultPage in searchPages) {
            result.addAll(resultPage.results)
        }
        return result
    }
}