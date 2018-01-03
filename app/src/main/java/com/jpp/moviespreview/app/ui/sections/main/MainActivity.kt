package com.jpp.moviespreview.app.ui.sections.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.ui.sections.main.playing.PlayingMoviesFragment
import com.jpp.moviespreview.app.util.extentions.addFragmentIfNotInStack

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addMainFragment()
    }


    private fun addMainFragment() {
        //TODO refactor this to support full screen capabilities
        addFragmentIfNotInStack(R.id.main_content, PlayingMoviesFragment.newInstance(), PlayingMoviesFragment.TAG)
    }
}
