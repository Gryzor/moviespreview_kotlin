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

    override fun linkView(licencesView: LicensesView) {
        with(moviesContext) {
            if (licenses == null) {
                executeUseCase(licencesView)
            } else {
                licencesView.showLicences(licenses!!)
            }
        }
    }

    override fun onLicenseSelected(license: License) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun executeUseCase(licencesView: LicensesView) {
        backgroundInteractor.executeBackgroundJob(
                {
                    useCase.execute()
                },
                {
                    if (it != null) {
                        moviesContext.licenses = mapper.convertDomainLicensesIntoUiLicenses(it)
                        licencesView.showLicences(moviesContext.licenses!!)
                    } else {
                        licencesView.showErrorLoadingLicenses()
                    }
                })
    }

}