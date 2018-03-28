package com.jpp.moviespreview.app.ui.sections.about.licenses

import com.jpp.moviespreview.app.ui.Error
import com.jpp.moviespreview.app.ui.License
import com.jpp.moviespreview.app.util.extentions.DelegatesExt

/**
 * Created by jpp on 1/20/18.
 */
interface LicensesView {
    fun showLicences(licences: List<License>)
    fun showErrorLoadingLicenses()
    fun showLicenseDetail(license: License)
}

interface LicensesPresenter {
    fun linkView(licencesView: LicensesView)
    fun onLicenseSelected(license: License)
}

interface LicensesPresenterInteractor {
    fun retrieveLicenses(licensesData: LicensesData)
}

/**
 * Defines a communication channel between presenter and interactor
 */
class LicensesData(onLicencesSetObserver: () -> Unit = {}) {
    var licences: List<License>? by DelegatesExt.observerDelegate(onLicencesSetObserver)
    var error: Error? by DelegatesExt.observerDelegate(onLicencesSetObserver)
}
