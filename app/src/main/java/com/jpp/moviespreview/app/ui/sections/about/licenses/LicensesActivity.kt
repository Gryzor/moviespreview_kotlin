package com.jpp.moviespreview.app.ui.sections.about.licenses

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.ui.License
import com.jpp.moviespreview.app.util.extentions.app
import com.jpp.moviespreview.app.util.extentions.showUnexpectedError
import kotlinx.android.synthetic.main.licences_activity.*
import javax.inject.Inject

/**
 * Created by jpp on 1/20/18.
 */
class LicensesActivity : AppCompatActivity(), LicensesView {


    private val component by lazy { app.aboutComponent() }

    @Inject
    lateinit var presenter: LicensesPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.licences_activity)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.open_source_action)

        component.inject(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.linkView(this)
    }

    override fun showLicences(licences: List<License>) {
        val layoutManager = LinearLayoutManager(this)
        rv_licences.layoutManager = layoutManager
        rv_licences.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
        rv_licences.adapter = LicensesAdapter(licences, { presenter.onLicenseSelected(it) })
    }

    override fun showErrorLoadingLicenses() {
        showUnexpectedError { finish() }
    }
}