package com.jpp.moviespreview.app.ui.sections.main.movies

import com.jpp.moviespreview.app.ui.*
import com.jpp.moviespreview.app.util.extentions.DelegatesExt

/**
 * Definition of the contract for the MVP implementation at the
 * movies section.
 *
 * For the moment, only has playing movies section, but the idea
 * is that this contract is generic enough to have all possible
 * movies section defined here.
 *
 * Created by jpp on 2/19/18.
 */

/**
 * Signature of the view that shows movies.
 */
interface MoviesView {
    fun showMoviePage(moviePage: MoviePage)
    fun showEndOfPaging()
    fun getScreenWidth(): Int
    fun showUnexpectedError()
    fun showNotConnectedToNetwork()
    fun isShowingMovies(): Boolean
    fun showLoading()
}

/**
 * Signature of the presenter to interact with the movies.
 */
interface MoviesPresenter {
    fun linkView(moviesView: MoviesView)
    fun getNextMoviePage()
    fun onMovieSelected(movie: Movie)
}

/**
 * Interactor definition to manage the interaction between [MoviesPresenter]
 * and the domain module.
 */
interface MoviesPresenterInteractor {
    fun configure(data: MoviesData, movieGenres: List<MovieGenre>, posterImageConfiguration: PosterImageConfiguration)
    fun retrieveMoviePage(page: Int)
}



/**
 * Defines a communication channel between [MoviesPresenter] and [MoviesPresenterInteractor].
 * The presenter will ask the interactor to do something and store the results in this class.
 * The interactor will execute the action(s) and will set each property of this class.
 * Using the property delegation system ([ObservableTypedDelegate]) the presenter is notified
 * about each property set on this class.
 */
class MoviesData(onValueSetObserver: () -> Unit = {}) {
    var lastMoviePage: MoviePage? by DelegatesExt.observerDelegate(onValueSetObserver)
    var error: Error? by DelegatesExt.observerDelegate(onValueSetObserver)
}