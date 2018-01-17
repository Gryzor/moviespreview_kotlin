package com.jpp.moviespreview.app.ui.sections.about

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.util.extentions.app
import kotlinx.android.synthetic.main.about_activity.*
import javax.inject.Inject

/**
 * Created by jpp on 1/17/18.
 */
class AboutActivity : AppCompatActivity(), AboutView {

    private val component by lazy { app.aboutComponent() }

    @Inject
    lateinit var presenter: AboutPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_activity)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.about_menu_item)

        component.inject(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.linkView(this)
    }

    override fun showAppVersion(appVersion: String) {
        about_screen_version_text_view.text = appVersion
    }
}