package com.jpp.moviespreview.app.domain.licenses

import com.jpp.moviespreview.app.data.cache.file.AssetLoader
import com.jpp.moviespreview.app.domain.Licenses
import com.jpp.moviespreview.app.domain.UseCase

/**
 * Use case to retrieve the [Licenses] from the device local storage.
 *
 * Created by jpp on 1/20/18.
 */
class RetrieveLicencesUseCase(private val assetLoader: AssetLoader,
                              private val mapper: LicencesDataMapper) : UseCase<Any, Licenses> {


    override fun execute(param: Any?): Licenses? {
        return assetLoader.loadLicences()?.let {
            mapper.convertDataLicencesIntoDomainLicences(it)
        }
    }

}