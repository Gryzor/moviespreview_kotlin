package com.jpp.moviespreview.app.ui.sections.about.licenses

import com.jpp.moviespreview.app.domain.Command
import com.jpp.moviespreview.app.domain.CommandData
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.Error
import com.jpp.moviespreview.app.util.extentions.whenNotNull
import com.jpp.moviespreview.app.domain.Licenses as DomainLicences

/**
 * [LicensesPresenterInteractor] implementation. Interacts with the domain layer to retrieve the [DomainLicences]
 * and map the domain classes to UI layer classes.
 *
 * Created by jpp on 3/28/18.
 */
class LicensesPresenterInteractorImpl(private val mapper: DomainToUiDataMapper,
                                      private val retrieveLicencesCommand: Command<Any, DomainLicences>) : LicensesPresenterInteractor {


    private val retrieveLicencesData = CommandData<DomainLicences>({ onLicencesRetrieveSuccess() }, { onLicencesRetrieveError() })
    private lateinit var licensesData: LicensesData

    override fun retrieveLicenses(licensesData: LicensesData) {
        this.licensesData = licensesData
        retrieveLicencesCommand.execute(retrieveLicencesData)
    }


    private fun onLicencesRetrieveSuccess() {
        with(mapper) {
            whenNotNull(retrieveLicencesData.value) {
                licensesData.licences = convertDomainLicensesIntoUiLicenses(it)
            }
        }
    }

    private fun onLicencesRetrieveError() {
        licensesData.error = Error(Error.UNKNOWN)
    }
}