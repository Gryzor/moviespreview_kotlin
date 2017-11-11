package com.jpp.moviespreview.app.ui.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jpp.moviespreview.app.ui.Movie
import com.jpp.moviespreview.app.util.extentions.app
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * Created by jpp on 11/11/17.
 */
class MovieDetailActivity : AppCompatActivity(), MovieDetailView {


    private val component by lazy { app.movieDetailsComponent() }

    @Inject
    lateinit var presenter: MovieDetailPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.linkView(this)
    }

    override fun showMovie(movie: Movie) {
        toast("Movie selected ${movie.originalTitle}")
    }

    override fun showMovieNotSelected() {
        toast("error")
    }

}