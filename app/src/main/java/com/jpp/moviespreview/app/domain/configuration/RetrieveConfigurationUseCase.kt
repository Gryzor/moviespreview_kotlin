package com.jpp.moviespreview.app.domain.configuration

import com.jpp.moviespreview.BuildConfig
import com.jpp.moviespreview.app.data.cache.MoviesCache
import com.jpp.moviespreview.app.data.server.MoviesPreviewApi
import com.jpp.moviespreview.app.domain.MoviesConfiguration
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.extentions.unwrapCall
import java.util.concurrent.TimeUnit

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
                                   private val api: MoviesPreviewApi,
                                   private val cache: MoviesCache) : UseCase<Any, MoviesConfiguration> {

    override fun execute(param: Any?): MoviesConfiguration? {
        return if (cache.isLastConfigOlderThan(TimeUnit.MINUTES.toMillis(30))) {
            api.getLastConfiguration(BuildConfig.API_KEY).unwrapCall {
                cache.saveMoviesConfig(it, System.currentTimeMillis())
                mapper.convertMoviesConfigurationFromDataModel(it)
            }
        } else {
            cache.getLastMovieConfiguration()?.let {
                mapper.convertMoviesConfigurationFromDataModel(it)
            }
        }
    }
}