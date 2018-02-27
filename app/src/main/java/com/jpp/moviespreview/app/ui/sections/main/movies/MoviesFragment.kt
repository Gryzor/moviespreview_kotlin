package com.jpp.moviespreview.app.ui.sections.main.movies

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.di.HasSubcomponentBuilders
import com.jpp.moviespreview.app.di.fragment.InjectedFragment
import com.jpp.moviespreview.app.ui.MoviePage
import com.jpp.moviespreview.app.ui.sections.main.movies.di.MoviesFragmentComponent
import com.jpp.moviespreview.app.ui.sections.splash.SplashActivity
import com.jpp.moviespreview.app.util.extentions.*
import kotlinx.android.synthetic.main.playing_movies_fragment.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject

/**
 * [MoviesView] implementation.
 *
 * It's implemented using a Fragment since the idea is to re-use all of this
 * functionality to show different sections of the application.
 *
 * Created by jpp on 2/21/18.
 */
class MoviesFragment : InjectedFragment(), MoviesView {

    companion object {
        const val TAG = "MoviesFragment"
        // Factory method to follow the Fragment.newInstance() Android pattern
        fun newInstance() = MoviesFragment()
    }

    private lateinit var transitionView: ImageView

    private val listAdapter by lazy {
        MoviesListAdapter({ movie, imageView ->
            moviesPresenter.onMovieSelected(movie)
            transitionView = imageView
        })
    }


    @Inject
    lateinit var moviesPresenter: MoviesPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        moviesPresenter.linkView(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.movies_fragment, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(view.ctx)
        rv_playing_movies.layoutManager = layoutManager
        rv_playing_movies.addItemDecoration(DividerItemDecoration(view.ctx, layoutManager.orientation))
        rv_playing_movies.adapter = listAdapter
        rv_playing_movies.endlessScrolling({ moviesPresenter.getNextMoviePage() })
    }


    override fun showMoviePage(moviePage: MoviePage) {
        loading_movies_view.hide()
        listAdapter.appendMovies(moviePage.results)
    }

    override fun backToSplashScreen() {
        activity.startActivity<SplashActivity>()
        activity.finish()
    }

    override fun showEndOfPaging() {
        // do nothing for the moment
    }

    override fun getScreenWidth(): Int = activity.getScreenSizeInPixels().x

    override fun showUnexpectedError() {
        activity.showUnexpectedError { activity.finish() }
    }

    override fun showNotConnectedToNetwork() {
        activity.showNoNetworkConnectionAlert()
    }

    override fun isShowingMovies(): Boolean = listAdapter.itemCount > 0

    override fun showLoading() {
        loading_movies_view.show()
    }


    override fun injectMembers(hasSubcomponentBuilders: HasSubcomponentBuilders) {
        (hasSubcomponentBuilders.getFragmentComponentBuilder(MoviesFragment::class.java) as MoviesFragmentComponent.Builder)
                .fragmentModule(MoviesFragmentComponent.MoviesFragmentModule(this)).build().injectMembers(this)
    }
}
