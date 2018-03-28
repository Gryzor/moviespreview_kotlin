package com.jpp.moviespreview.app.ui.sections.about.licenses

import com.jpp.moviespreview.app.ui.License
import com.jpp.moviespreview.app.ui.MoviesContextHandler
import com.jpp.moviespreview.app.ui.interactors.BackgroundExecutor
import com.jpp.moviespreview.app.util.extentions.whenNotNull

/**
 * [LicensesPresenter] implementation. Will interact with the domain layer via the [interactor] provided
 * to show the licenses using the [LicensesView]
 *
 * Created by jpp on 1/20/18.
 */
class LicensesPresenterImpl(private val moviesContextHandler: MoviesContextHandler,
                            private val interactor: LicensesPresenterInteractor,
                            private val backgroundExecutor: BackgroundExecutor,
                            private val licencesFlowResolver: LicencesFlowResolver) : LicensesPresenter {

    private lateinit var viewInstance: LicensesView
    private val licencesData by lazy { LicensesData({ backgroundExecutor.executeUiJob { observeData() } }) }


    override fun linkView(licencesView: LicensesView) {
        viewInstance = licencesView
        with(moviesContextHandler) {
            getLicenses()?.let {
                viewInstance.showLicences(it)
            } ?: run {
                backgroundExecutor.executeBackgroundJob { interactor.retrieveLicenses(licencesData) }
            }
        }
    }

    override fun onLicenseSelected(license: License) {
        licencesFlowResolver.showLicenseDetail(license)
    }

    private fun observeData() {
        with(licencesData) {
            whenNotNull(licences) {
                moviesContextHandler.addLicenses(it)
                viewInstance.showLicences(it)
            }

            whenNotNull(error) {
                viewInstance.showErrorLoadingLicenses()
            }
        }
    }

}