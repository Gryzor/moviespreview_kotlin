package com.jpp.moviespreview.app.ui.sections.detail.body

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.ui.MovieGenre
import com.jpp.moviespreview.app.ui.recyclerview.SimpleDividerItemDecoration
import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailPresenter
import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailView
import com.jpp.moviespreview.app.util.extentions.app
import kotlinx.android.synthetic.main.movie_details_fragment.*
import javax.inject.Inject

/**
 * Shows the details of a given movie. Note that this [Fragment] does not interacts
 * with the movie's images nor with the cast of the movie.
 *
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
        movie_details_fragment_recycler_view.addItemDecoration(SimpleDividerItemDecoration(activity))
        movie_details_fragment_recycler_view.adapter = MovieDetailsGenreAdapter(genres)
    }

    override fun showMovieVoteCount(voteCount: Double) {
        movie_details_fragment_vote_count_text_view.text = voteCount.toString()
    }

    override fun showMoviePopularity(popularity: Float) {
        movie_details_popularity_text_view.text = popularity.toString()
    }

}