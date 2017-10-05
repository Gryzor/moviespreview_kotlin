package com.jpp.moviespreview.app.domain.configuration

import com.jpp.moviespreview.BuildConfig
import com.jpp.moviespreview.app.data.server.MoviesPreviewApi
import com.jpp.moviespreview.app.domain.MoviesConfiguration
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.ui.extentions.unwrapCall

/**
 * TODO 1 - Verify if cache data is valid
 * TODO 2 - If cached data is valid, return cached data
 * TODO 3 - If cached data is not valid, retrieve remote data
 * TODO 4 - Cache newly retrieved data
 * TODO 5 - Map between domain model and UI model
 * TODO 6 - Return mapped UI data
 * Created by jpp on 10/5/17.
 */
class RetrieveConfigurationUseCase(private val mapper: ConfigurationDataMapper,
                                   private val api: MoviesPreviewApi) : UseCase<Any, MoviesConfiguration> {

    override fun execute(param: Any?): MoviesConfiguration? {
        return api.getLastConfiguration(BuildConfig.API_KEY).unwrapCall {
            mapper.convertMoviesConfigurationFromDataModel(it)
        }
    }
}