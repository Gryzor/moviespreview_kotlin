package com.jpp.moviespreview.app.ui.sections.search

import android.util.Log

/**
 * Created by jpp on 1/6/18.
 */
class MultiSearchPresenterImpl(private val querySubmitManager: QuerySubmitManager) : MultiSearchPresenter {

    override fun linkView(multiSearchView: MultiSearchView) {
        querySubmitManager
                .linkQueryTextView(
                        multiSearchView.getQueryTextView(),
                        {
                            query -> Log.d("Query", query)
                        })
    }

}