package com.jpp.moviespreview.app.ui.sections.main.search

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.util.extentions.app
import kotlinx.android.synthetic.main.search_movies_activity.*

/**
 * Created by jpp on 1/6/18.
 */
class MultiSearchActivity : AppCompatActivity() {


    private val component by lazy { app.multiSearchComponent() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_movies_activity)

        setSupportActionBar(search_activity_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        search_view.isIconified = false
        search_view.setIconifiedByDefault(false)



        component.inject(this)
    }

}