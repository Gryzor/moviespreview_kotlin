package com.jpp.moviespreview.app.ui.sections.about.licenses

import com.jpp.moviespreview.app.ui.License

/**
 * Created by jpp on 1/20/18.
 */
interface LicensesView {
    fun showLicences(licences: List<License>)
    fun showErrorLoadingLicenses()
}

interface LicensesPresenter {
    fun linkView(licencesView: LicensesView)
    fun onLicenseSelected(license: License)
}