package com.jpp.moviespreview.app.ui.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.util.extentions.app
import com.jpp.moviespreview.app.util.extentions.loadImageUrl
import kotlinx.android.synthetic.main.movie_detail_activity.*
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
        setContentView(R.layout.movie_detail_activity)
        component.inject(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.linkView(this)
    }

    override fun showMovieImages(vararg urls: String) {
        iv_movie_details.loadImageUrl(urls[0])
    }

    override fun showMovieNotSelected() {
        toast("error")
    }

}