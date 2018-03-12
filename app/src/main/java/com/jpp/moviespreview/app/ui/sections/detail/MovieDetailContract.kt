package com.jpp.moviespreview.app.ui.sections.detail

import com.jpp.moviespreview.app.ui.*
import com.jpp.moviespreview.app.ui.interactors.PresenterInteractorDelegate
import com.jpp.moviespreview.app.util.extentions.DelegatesExt

/************************************************************
 ******** Contract definition for the details body **********
 ******** This section controls the first page **************
 ************************************************************/
interface MovieDetailView {
    fun showMovieOverview(overview: String)
    fun showMovieGenres(genres: List<MovieGenre>)
    fun showMoviePopularity(popularity: Float)
    fun showMovieVoteCount(voteCount: Double)
}

interface MovieDetailPresenter {
    fun linkView(movieDetailView: MovieDetailView)
}


/***********************************************************************************
 ******** Contract definition for the images section in the movie details **********
 ************ This section controls the header part of the screen  *****************
 ***********************************************************************************/
interface MovieDetailImagesView {
    fun showMovieImage(imageUrl: String)
    fun showMovieTitle(movieTitle: String)
    fun showMovieNotSelected()
}

interface MovieDetailImagesPresenter {
    fun linkView(movieDetailView: MovieDetailImagesView)
}


/***********************************************************************************
 ******** Contract definition for the credits section in the movie details *********
 ************ This section controls the header part of the screen  *****************
 ***********************************************************************************/
interface MovieDetailCreditsView {
    fun showLoading()
    fun showMovieCredits(credits: List<CreditPerson>)
    fun showErrorRetrievingCredits()
    fun getTargetProfileImageHeight(): Int
}

interface MovieDetailCreditsPresenter {
    fun linkView(view: MovieDetailCreditsView)
}

/**
 * Interactor for the Movie credits section. It takes care of interacting with the domain
 * layer and adapts the data from domain to UI layer.
 */
interface MovieDetailCreditsInteractor {
    fun retrieveMovieCredits(creditsData: CreditsData, movie: Movie, profileImageConfig: ProfileImageConfiguration)
}


/**
 * Defines a communication channel between the [MovieDetailCreditsPresenter] and the [MovieDetailCreditsInteractor].
 * The presenter will ask the interactor to do something and store the results in this class.
 * The interactor will execute the action(s) and will set each property of this class.
 * Using the property delegation system ([ObservableTypedDelegate]) the presenter is notified
 * about each property set on this class.
 */
class CreditsData(onValueSetObserver: () -> Unit = {}) {
    var credits: List<CreditPerson>? by DelegatesExt.observerDelegate(onValueSetObserver)
    var error: Error? by DelegatesExt.observerDelegate(onValueSetObserver)
}