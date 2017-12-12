package com.jpp.moviespreview.app.ui.main.playing

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.ui.Movie
import com.jpp.moviespreview.app.ui.MoviePage
import com.jpp.moviespreview.app.ui.detail.MovieDetailActivity
import com.jpp.moviespreview.app.ui.splash.SplashActivity
import com.jpp.moviespreview.app.util.extentions.*
import kotlinx.android.synthetic.main.playing_movies_fragment.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject


/**
 * Contains all the logic to show the movies currently playing in theaters.
 *
 * Created by jpp on 10/23/17.
 */
class PlayingMoviesFragment : Fragment(), PlayingMoviesView {


    private val adapter by lazy {
        PlayingMoviesAdapter(
                {
                    movie: Movie, viewPager: ViewPager ->
                    ViewCompat.setTransitionName(viewPager, "vpTransition")
                    playingMoviesPresenter.onMovieSelected(movie)
                    showMovieDetails(viewPager)
                },
                { movie: Movie, position: Int ->
                    playingMoviesPresenter.onMovieImageSelected(movie, position)
                })
    }

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
            = inflater.inflate(R.layout.playing_movies_fragment, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(view.ctx)
        rv_playing_movies.layoutManager = layoutManager
        rv_playing_movies.addItemDecoration(DividerItemDecoration(view.ctx, layoutManager.orientation))
        rv_playing_movies.adapter = adapter
        rv_playing_movies.endlessScrolling({ playingMoviesPresenter.getNextMoviePage() })
    }

    override fun onResume() {
        super.onResume()
        playingMoviesPresenter.refreshData()
    }

    override fun showMoviePage(moviePage: MoviePage) {
        loading_movies_view.hide()
        adapter.appendMovies(moviePage.results)
    }

    override fun updateMovie(movie: Movie) {
        adapter.refreshMovie(movie)
    }

    override fun backToSplashScreen() {
        activity.startActivity<SplashActivity>()
        activity.finish()
    }

    override fun getScreenWidth(): Int = activity.getScreenSizeInPixels().x

    override fun showUnexpectedError() {
        activity.showUnexpectedError { activity.finish() }
    }

    override fun showNotConnectedToNetwork() {
        activity.showNoNetworkConnectionAlert()
    }

    override fun showEndOfPaging() {
        // do nothing for the moment
    }

    override fun showInitialLoading() {
        loading_movies_view.show()
    }

    private fun showMovieDetails(viewPager: ViewPager) {
        MovieDetailActivity.navigateWithTransition(activity as AppCompatActivity, viewPager)
    }
}