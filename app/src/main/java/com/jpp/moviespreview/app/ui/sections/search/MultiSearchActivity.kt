package com.jpp.moviespreview.app.ui.sections.search

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.ui.MultiSearchResult
import com.jpp.moviespreview.app.util.extentions.app
import kotlinx.android.synthetic.main.search_movies_activity.*
import org.jetbrains.anko.longToast
import javax.inject.Inject

/**
 * Created by jpp on 1/6/18.
 */
class MultiSearchActivity : AppCompatActivity(), MultiSearchView {

    private val component by lazy { app.multiSearchComponent() }

    @Inject
    lateinit var presenter: MultiSearchPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_movies_activity)

        setSupportActionBar(search_activity_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        search_view.isIconified = false
        search_view.setIconifiedByDefault(false)


        component.inject(this)
    }


    override fun onResume() {
        super.onResume()
        presenter.linkView(this)
    }

    override fun getQueryTextView(): QueryTextView = QueryTextViewImpl(search_view)

    override fun showResults(results: List<MultiSearchResult>) {
        longToast("Result ${results.size}")
    }


    /*
     * Inner implementation of QueryTextView. It will add itself as OnQueryTextListener of
     * the provided SearchView and will update the QueryTextListener with each respective query.
     */
    private class QueryTextViewImpl(searchView: SearchView) : QueryTextView, SearchView.OnQueryTextListener {

        private lateinit var queryTextListener: QueryTextListener

        override fun addQueryTextListener(listener: QueryTextListener) {
            queryTextListener = listener
        }

        override fun onQueryTextSubmit(query: String?): Boolean =
                queryTextListener.onQueryTextSubmit(query)

        override fun onQueryTextChange(newText: String?): Boolean =
                queryTextListener.onQueryTextChange(newText)

        init {
            searchView.setOnQueryTextListener(this)
        }
    }

}