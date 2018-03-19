package com.jpp.moviespreview.app.ui.sections.search

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.di.HasSubcomponentBuilders
import com.jpp.moviespreview.app.di.activity.InjectedActivity
import com.jpp.moviespreview.app.ui.MultiSearchResult
import com.jpp.moviespreview.app.ui.recyclerview.SimpleDividerItemDecoration
import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailActivity
import com.jpp.moviespreview.app.ui.sections.search.di.MultiSearchActivityComponent
import com.jpp.moviespreview.app.util.extentions.*
import kotlinx.android.synthetic.main.multi_search_activity.*
import javax.inject.Inject

/**
 * Presents the searchFirstPage user interface for the user to searchFirstPage movies, tv shows
 * and people.
 *
 * Created by jpp on 1/6/18.
 */
class MultiSearchActivity : InjectedActivity(), MultiSearchView {


    override fun injectMembers(hasSubcomponentBuilders: HasSubcomponentBuilders) {
        (hasSubcomponentBuilders.getActivityComponentBuilder(MultiSearchActivity::class.java) as MultiSearchActivityComponent.Builder)
                .activityModule(MultiSearchActivityComponent.MultiSearchActivityModule(this)).build().injectMembers(this)
    }


    private val adapter by lazy {
        MultiSearchAdapter({ selectedItem: MultiSearchResult, view: ImageView ->
            transitionView = view
            presenter.onItemSelected(selectedItem)
        })
    }

    @Inject
    lateinit var presenter: MultiSearchPresenter

    private lateinit var transitionView: ImageView

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

    override fun getQueryTextView(): QueryTextView = QueryTextViewImpl(search_view)

    override fun showResults(results: List<MultiSearchResult>) {
        adapter.showResults(results)
    }

    /*
     * Loading this images in such a big configuration is only
     * to favor the transition to details
     */
    override fun getTargetMultiSearchResultImageSize() = getScreenSizeInPixels().x

    override fun appendResults(results: List<MultiSearchResult>) {
        adapter.appendResults(results)
    }

    override fun showEndOfPaging() {
        // do nothing for the moment
    }

    override fun clearPages() {
        search_view.setQuery("", false)
        adapter.clear()
    }

    override fun showMovieDetails() {
        MovieDetailActivity.navigateWithTransition(this, transitionView)
    }

    override fun showUnexpectedError() {
        showUnexpectedError { finish() }
    }

    override fun showNotConnectedToNetwork() {
        showNoNetworkConnectionAlert()
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