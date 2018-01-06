package com.jpp.moviespreview.app.ui.sections.main.search

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu

import com.jpp.moviespreview.R

/**
 * Created by jpp on 1/6/18.
 */
class SearchMoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_activity_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.isIconified = false
        searchView.setIconifiedByDefault(false)
        Log.d("Search", "Name -> ${searchView.id}")
        return true
    }
}