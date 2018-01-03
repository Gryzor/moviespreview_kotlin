package com.jpp.moviespreview.app.ui.detail

import com.jpp.moviespreview.app.ui.CreditPerson
import com.jpp.moviespreview.app.ui.MovieGenre

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
    fun showMovieImages(imagesUrl: List<String>, selectedPosition: Int)
    fun showMovieTitle(movieTitle: String)
    fun showMovieNotSelected()
}

interface MovieDetailImagesPresenter {
    fun linkView(movieDetailView: MovieDetailImagesView)
    fun onMovieImageSelected(position: Int)
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