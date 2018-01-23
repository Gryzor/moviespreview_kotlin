package com.jpp.moviespreview.app.ui.sections.about.licenses

import com.jpp.moviespreview.app.domain.Licenses
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.License
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.interactors.BackgroundInteractor

/**
 * Created by jpp on 1/20/18.
 */
class LicensesPresenterImpl(private val moviesContext: MoviesContext,
                            private val useCase: UseCase<Any, Licenses>,
                            private val mapper: DomainToUiDataMapper,
                            private val backgroundInteractor: BackgroundInteractor) : LicensesPresenter {

    private lateinit var viewInstance: LicensesView

    override fun linkView(licencesView: LicensesView) {
        viewInstance = licencesView
        with(moviesContext) {
            if (licenses == null) {
                executeUseCase()
            } else {
                viewInstance.showLicences(licenses!!)
            }
        }
    }

    override fun onLicenseSelected(license: License) {
        viewInstance.showLicenseDetail(license)
    }

    private fun executeUseCase() {
        backgroundInteractor.executeBackgroundJob(
                {
                    useCase.execute()
                },
                {
                    if (it != null) {
                        moviesContext.licenses = mapper.convertDomainLicensesIntoUiLicenses(it)
                        viewInstance.showLicences(moviesContext.licenses!!)
                    } else {
                        viewInstance.showErrorLoadingLicenses()
                    }
                })
    }

}