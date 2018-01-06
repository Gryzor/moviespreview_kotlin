package com.jpp.moviespreview.app.ui.sections.search

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

/**
 * [QuerySubmitManager] implementation that listen updates from a [QueryTextView] and will
 * react to it.
 * Internally, uses Rx to listen for updates, wait until the user stops typing and then
 * pass the query to an executable function.
 *
 * Created by jpp on 1/6/18.
 */
class QuerySubmitManagerImpl : QuerySubmitManager {


    override fun linkQueryTextView(queryTextView: QueryTextView, action: (String) -> Unit) {
        RxQueryTextListener(queryTextView)
                .getObservableInstance()
                .debounce(1, TimeUnit.SECONDS) // wait one second
                .filter { item -> item.length > 3 } // at least 3 characters
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { query -> action(query) }
    }


    private class RxQueryTextListener(queryTextView: QueryTextView) : QueryTextListener {

        private val subject = BehaviorSubject.create("")

        init {
            queryTextView.addQueryTextListener(this)
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            if (newText != null && !newText.isEmpty()) {
                subject.onNext(newText)
            }
            return true
        }

        override fun onQueryTextSubmit(query: String?): Boolean {
            subject.onCompleted()
            return true
        }

        fun getObservableInstance(): Observable<String> = subject
    }

}
