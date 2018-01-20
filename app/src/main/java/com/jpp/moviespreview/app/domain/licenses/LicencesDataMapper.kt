package com.jpp.moviespreview.app.domain.licenses

import com.jpp.moviespreview.app.domain.License
import com.jpp.moviespreview.app.domain.Licenses
import com.jpp.moviespreview.app.data.Licenses as DataLicences
import com.jpp.moviespreview.app.data.License as DataLicence

/**
 * Maps licences classes from the data layer to the domain layer
 *
 * Created by jpp on 1/20/18.
 */
class LicencesDataMapper {


    /**
     * Converts a [DataLicences] object into a domain [Licenses] object.
     */
    fun convertDataLicencesIntoDomainLicences(dataLicenses: DataLicences) = with(dataLicenses) {
        Licenses(convertDataLicenseListIntoDomainLicenceList(licenses))
    }

    private fun convertDataLicenseListIntoDomainLicenceList(dataLicenceList: List<DataLicence>): List<License> {
        return dataLicenceList.mapTo(ArrayList()) {
            License(it.id, it.name, it.url)
        }
    }


}