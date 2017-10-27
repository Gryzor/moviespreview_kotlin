package com.jpp.moviespreview.app.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.ui.main.playing.PlayingMoviesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addMainFragment()
    }


    private fun addMainFragment() {
        //TODO refactor this to support full screen capabilities
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.main_content, PlayingMoviesFragment.newInstance(), PlayingMoviesFragment.TAG)
        transaction.commit()
    }
}
