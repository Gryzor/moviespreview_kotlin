package com.jpp.moviespreview.app.ui.sections.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.ui.sections.about.AboutActivity
import com.jpp.moviespreview.app.ui.sections.main.playing.PlayingMoviesFragment
import com.jpp.moviespreview.app.ui.sections.search.MultiSearchActivity
import com.jpp.moviespreview.app.util.extentions.addFragmentIfNotInStack

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        addMainFragment()
    }


    private fun addMainFragment() {
        //TODO refactor this to support full screen capabilities
        addFragmentIfNotInStack(R.id.main_content, PlayingMoviesFragment.newInstance(), PlayingMoviesFragment.TAG)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_search -> {
            startActivity(Intent(this, MultiSearchActivity::class.java))
            true
        }
        R.id.action_about -> {
            startActivity(Intent(this, AboutActivity::class.java))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
