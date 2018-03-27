package com.jpp.moviespreview.app.ui.sections.about.licenses

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.di.HasSubcomponentBuilders
import com.jpp.moviespreview.app.di.activity.InjectedActivity
import com.jpp.moviespreview.app.ui.License
import com.jpp.moviespreview.app.ui.sections.about.licenses.di.LicensesActivityComponent
import com.jpp.moviespreview.app.util.extentions.showUnexpectedError
import com.jpp.moviespreview.app.util.extentions.whenNotNull
import kotlinx.android.synthetic.main.licences_activity.*
import javax.inject.Inject

/**
 * Activity used to show the licenses list of all the libraries used by the application
 *
 * Created by jpp on 1/20/18.
 */
class LicensesActivity : InjectedActivity(), LicensesView {


    override fun injectMembers(hasSubcomponentBuilders: HasSubcomponentBuilders) {
        (hasSubcomponentBuilders.getActivityComponentBuilder(LicensesActivity::class.java) as LicensesActivityComponent.Builder)
                .activityModule(LicensesActivityComponent.LicencesActivityModule(this)).build().injectMembers(this)
    }


    @Inject
    lateinit var presenter: LicensesPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.licences_activity)

        whenNotNull(supportActionBar) {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = getString(R.string.open_source_action)
        }

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

    override fun showLicenseDetail(license: License) {
        with(license) {
            LicensesDialogFragment.newInstance(name, url).show(supportFragmentManager, "TAG")
        }
    }
}