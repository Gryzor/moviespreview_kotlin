package com.jpp.moviespreview.app.ui.main.playing

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.ui.MoviePage
import com.jpp.moviespreview.app.util.extentions.app
import javax.inject.Inject


/**
 * Contains all the logic to show the movies currently playing in theaters.
 *
 * Created by jpp on 10/23/17.
 */
class PlayingMoviesFragment : Fragment(), PlayingMoviesView {


    companion object {
        val TAG = "PlayingMoviesFragment"
        // Factory method to follow the Fragment.newInstance() Android pattern
        fun newInstance() = PlayingMoviesFragment()
    }


    @Inject
    lateinit var playingMoviesPresenter: PlayingMoviesPresenter


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity.app.playingMoviesComponent().inject(this)
        playingMoviesPresenter.linkView(this)
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.playing_movies_fragment, container, false)
        return view
    }


    override fun showMoviePage(moviePage: MoviePage) {
        Toast.makeText(activity, "Size ${moviePage.results.size}", Toast.LENGTH_LONG).show()
    }

    override fun backToSplashScreen() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}