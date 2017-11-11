package com.jpp.moviespreview.app.ui.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jpp.moviespreview.app.ui.Movie
import com.jpp.moviespreview.app.util.extentions.app

/**
 * Created by jpp on 11/11/17.
 */
class MovieDetailActivity : AppCompatActivity(), MovieDetailView {

    private val component by lazy { app.movieDetailsComponent() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
    }

    override fun showMovie(movie: Movie) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}