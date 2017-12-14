package com.jpp.moviespreview.app.ui.detail.body

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.ui.MovieGenre
import com.jpp.moviespreview.app.ui.detail.MovieDetailPresenter
import com.jpp.moviespreview.app.ui.detail.MovieDetailView
import com.jpp.moviespreview.app.ui.recyclerview.VerticalSpaceItemDecoration
import com.jpp.moviespreview.app.util.extentions.app
import kotlinx.android.synthetic.main.movie_details_fragment.*
import javax.inject.Inject

/**
 * Created by jpp on 12/13/17.
 */
class MovieDetailsFragment : Fragment(), MovieDetailView {


    companion object {
        // Factory method to follow the Fragment.newInstance() Android pattern
        fun newInstance() = MovieDetailsFragment()
    }

    @Inject
    lateinit var presenter: MovieDetailPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity.app.movieDetailsComponent().inject(this)
        presenter.linkView(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.movie_details_fragment, container, false)
    }


    override fun showMovieOverview(overview: String) {
        movie_details_fragment_overview_body.text = overview
    }

    override fun showMovieGenres(genres: List<MovieGenre>) {
        val layoutManager = LinearLayoutManager(activity)
        movie_details_fragment_recycler_view.layoutManager = layoutManager
        movie_details_fragment_recycler_view.addItemDecoration(VerticalSpaceItemDecoration(resources.getDimension(R.dimen.default_vertical_recycler_view_space).toInt()))
        movie_details_fragment_recycler_view.adapter = MovieDetailsGenreAdapter(genres)
    }
}