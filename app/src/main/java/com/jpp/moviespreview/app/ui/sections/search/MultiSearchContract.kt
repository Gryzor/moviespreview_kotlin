package com.jpp.moviespreview.app.ui.sections.search

import com.jpp.moviespreview.app.ui.*
import com.jpp.moviespreview.app.ui.interactors.PaginationController
import com.jpp.moviespreview.app.ui.interactors.PresenterInteractorDelegate
import com.jpp.moviespreview.app.util.extentions.DelegatesExt

/**
 * Contains the MVP contract for themulti searchFirstPage section
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
    fun clearSearch()
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
 * Interactor definition to manage the interaction between [MultiSearchPresenter]
 * and the domain module.
 */
interface MultiSearchInteractor {
    fun configure(data: MultiSearchData, movieGenres: List<MovieGenre>, posterImageConfig: PosterImageConfiguration, profileImageConfig: ProfileImageConfiguration)
    fun searchFirstPage(query: String)
    fun searchPage(query: String, page: Int)
}


/**
 * Defines a communication channel between [MultiSearchPresenter] and [MultiSearchInteractor].
 * The presenter will ask the interactor to do something and store the results in this class.
 * The interactor will execute the action(s) and will set each property of this class.
 * Using the property delegation system ([ObservableTypedDelegate]) the presenter is notified
 * about each property set on this class.
 */
class MultiSearchData(onValueSetObserver: () -> Unit = {}) {
    var lastSearchPage: MultiSearchPage? by DelegatesExt.observerDelegate(onValueSetObserver)
    var error: Error? by DelegatesExt.observerDelegate(onValueSetObserver)
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
 * Resolves the flow in the search section
 */
interface MultiSearchFlowResolver {
    fun showMovieDetails()
}


/**
 * Defines an interactor to provide support to the multi searchFirstPage section.
 */
interface MultiSearchPresenterController : PresenterInteractorDelegate, PaginationController {

    fun findProfileImageConfigurationForHeight(profileImageConfigs: List<ProfileImageConfiguration>,
                                               height: Int): ProfileImageConfiguration

    fun findPosterImageConfigurationForWidth(posterImageConfigs: List<PosterImageConfiguration>,
                                             width: Int): PosterImageConfiguration
}