package com.jpp.moviespreview.app.ui.sections.about

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jpp.moviespreview.R

/**
 * Created by jpp on 1/17/18.
 */
class AboutActivity : AppCompatActivity(), AboutView {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_activity)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.about_menu_item)
    }
}