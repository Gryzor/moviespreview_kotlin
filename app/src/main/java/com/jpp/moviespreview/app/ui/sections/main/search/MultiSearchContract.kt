package com.jpp.moviespreview.app.ui.sections.main.search

/**
 * Contains the MVP contract for themulti search section
 *
 * Created by jpp on 1/6/18.
 */

interface MultiSearchView {

}

interface MultiSearchPresenter {
    fun linkView(multiSearchView: MultiSearchView)
}

interface QueryTextListener {
    fun onQueryTextSubmit(query: String): Boolean
    fun onQuieryTextChange(newText: String): Boolean
}