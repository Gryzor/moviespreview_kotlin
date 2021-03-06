package com.jpp.moviespreview.app.ui.sections.search

import com.jpp.moviespreview.app.ui.MultiSearchResult
import com.jpp.moviespreview.app.ui.PosterImageConfiguration
import com.jpp.moviespreview.app.ui.ProfileImageConfiguration
import com.jpp.moviespreview.app.ui.interactors.PaginationInteractor
import com.jpp.moviespreview.app.ui.interactors.PresenterInteractorDelegate

/**
 * Contains the MVP contract for themulti search section
 *
 * Created by jpp on 1/6/18.
 */

interface MultiSearchView {
    fun getQueryTextView(): QueryTextView
    fun showResults(results: List<MultiSearchResult>)
    fun appendResults(results: List<MultiSearchResult>)
    fun getTargetMultiSearchResultImageSize(): Int
    fun showEndOfPaging()
    fun clearPages()
    fun showMovieDetails()
    fun showUnexpectedError()
    fun showNotConnectedToNetwork()
}

interface MultiSearchPresenter {
    fun linkView(multiSearchView: MultiSearchView)
    fun getNextSearchPage()
    fun clearLastSearch()
    fun onItemSelected(selectedItem: MultiSearchResult)
}


/**
 * Manager to handle query submits. It will
 * link a [QueryTextView] with a [QueryTextListener] and will
 * react to events from it.
 */
interface QuerySubmitManager {
    fun linkQueryTextView(queryTextView: QueryTextView, action: (String) -> Unit)
}

/**
 * Defines the interface of a View that will receive updates and
 * will redirect the query text to the provided [QueryTextListener]
 */
interface QueryTextView {
    fun addQueryTextListener(listener: QueryTextListener)
}

/**
 * Defines the interface of a subject that will listen for updates from
 * [QueryTextView] and it will react accordingly.
 */
interface QueryTextListener {
    fun onQueryTextSubmit(query: String?): Boolean
    fun onQueryTextChange(newText: String?): Boolean
}


/**
 * Defines an interactor to provide support to the multi search section.
 */
interface MultiSearchPresenterInteractor : PresenterInteractorDelegate, PaginationInteractor {

    fun findProfileImageConfigurationForHeight(profileImageConfigs: List<ProfileImageConfiguration>,
                                               height: Int): ProfileImageConfiguration

    fun findPosterImageConfigurationForWidth(posterImageConfigs: List<PosterImageConfiguration>,
                                             width: Int): PosterImageConfiguration
}