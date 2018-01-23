package com.jpp.moviespreview.app.ui.sections.detail

import com.jpp.moviespreview.app.ui.CreditPerson
import com.jpp.moviespreview.app.ui.MovieGenre
import com.jpp.moviespreview.app.ui.ProfileImageConfiguration
import com.jpp.moviespreview.app.ui.interactors.PresenterInteractorDelegate

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

interface MovieDetailsCreditsPresenterInteractor : PresenterInteractorDelegate {

    fun findProfileImageConfigurationForHeight(profileImageConfigs: List<ProfileImageConfiguration>,
                                               height: Int): ProfileImageConfiguration

}