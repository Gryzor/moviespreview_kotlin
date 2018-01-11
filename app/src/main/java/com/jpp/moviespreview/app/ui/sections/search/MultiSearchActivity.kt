package com.jpp.moviespreview.app.ui.sections.search

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.ui.MultiSearchResult
import com.jpp.moviespreview.app.ui.recyclerview.SimpleDividerItemDecoration
import com.jpp.moviespreview.app.util.extentions.app
import com.jpp.moviespreview.app.util.extentions.endlessScrolling
import kotlinx.android.synthetic.main.multi_search_activity.*
import org.jetbrains.anko.longToast
import javax.inject.Inject

/**
 * TODO DETAILS
 * TODO hint search
 *
 * Created by jpp on 1/6/18.
 */
class MultiSearchActivity : AppCompatActivity(), MultiSearchView {


    private val component by lazy { app.multiSearchComponent() }
    private val adapter by lazy { MultiSearchAdapter() }

    @Inject
    lateinit var presenter: MultiSearchPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.multi_search_activity)

        setSupportActionBar(search_activity_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setupSearchView()


        val layoutManager = LinearLayoutManager(this)
        search_results_recycler_view.layoutManager = layoutManager
        search_results_recycler_view.addItemDecoration(SimpleDividerItemDecoration(this))
        search_results_recycler_view.adapter = adapter
        search_results_recycler_view.endlessScrolling({ presenter.getNextSearchPage() })

        component.inject(this)
    }

    private fun setupSearchView() {
        search_view.isIconified = false
        search_view.setIconifiedByDefault(false)


        // we need to find the button this way b/c we're using isIconified = false
        val closeButton = search_view.findViewById<View>(android.support.v7.appcompat.R.id.search_close_btn)
        closeButton.setOnClickListener {
            presenter.clearLastSearch()
        }
    }


    override fun onResume() {
        super.onResume()
        presenter.linkView(this)
    }

    override fun getQueryTextView(): QueryTextView = QueryTextViewImpl(search_view)

    override fun showResults(results: List<MultiSearchResult>) {
        adapter.showResults(results)
    }

    override fun getTargetMultiSearchResultImageSize() = resources.getDimensionPixelSize(R.dimen.multi_search_result_image_size)

    override fun appendResults(results: List<MultiSearchResult>) {
        adapter.appendResults(results)
    }

    override fun showEndOfPaging() {
        //TODO implement me
        longToast("End of paging")
    }

    override fun clearPages() {
        search_view.setQuery("", false)
        adapter.clear()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> presenter.clearLastSearch()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        presenter.clearLastSearch()
        super.onBackPressed()
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