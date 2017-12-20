package com.jpp.moviespreview.app.ui.detail.credits

import android.os.Bundle
import android.support.v4.app.Fragment
import com.jpp.moviespreview.app.ui.CreditPerson
import com.jpp.moviespreview.app.ui.detail.MovieDetailCreditsPresenter
import com.jpp.moviespreview.app.ui.detail.MovieDetailCreditsView
import com.jpp.moviespreview.app.util.extentions.app

/**
 * Created by jpp on 12/20/17.
 */
class MovieCreditsFragment : Fragment(), MovieDetailCreditsView {


    companion object {
        // Factory method to follow the Fragment.newInstance() Android pattern
        fun newInstance() = MovieCreditsFragment()
    }

    private lateinit var presenter: MovieDetailCreditsPresenter


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity.app.movieDetailsComponent().inject(this)
        presenter.linkView(this)
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMovieCredits(credits: List<CreditPerson>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showErrorRetrievingCredits() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}