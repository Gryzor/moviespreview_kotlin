package com.jpp.moviespreview.app.domain.licenses

import com.jpp.moviespreview.app.data.cache.file.AssetLoader
import com.jpp.moviespreview.app.domain.Command
import com.jpp.moviespreview.app.domain.CommandData
import com.jpp.moviespreview.app.domain.Licenses

/**
 * [Command] executed to retrieve the licences stored:
 * The license list is stored in the assets of the application's bundle.
 * This command uses the [AssetLoader] provided to retrieve the licences and notifies
 * the clients using the [CommandData].
 *
 * Created by jpp on 3/27/18.
 */
class RetrieveLicensesCommand(private val assetLoader: AssetLoader,
                              private val mapper: LicensesDataMapper) : Command<Any, Licenses> {

    override fun execute(data: CommandData<Licenses>, param: Any?) {
        assetLoader.loadLicences()?.let {
            data.value = mapper.convertDataLicencesIntoDomainLicences(it)
        } ?: run {
            data.error = IllegalStateException("Can not retrieve licenses at this time")
        }
    }
}