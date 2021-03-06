package com.jpp.moviespreview.app.domain.configuration

import com.jpp.moviespreview.app.data.cache.MoviesConfigurationCache
import com.jpp.moviespreview.app.data.server.MoviesPreviewApiWrapper
import com.jpp.moviespreview.app.domain.MoviesConfiguration
import com.jpp.moviespreview.app.domain.UseCase

/**
 * Use case executed to retrieve the last available configuration:
 * It will verify if there's a [MoviesConfiguration] instance in the [configurationCache] that it's not 'too old'.
 * If there is not a valid configuration in the [configurationCache], it will retrieve a new one from
 * the [api] and it will store it in the [configurationCache].
 *
 * Created by jpp on 10/5/17.
 */
class RetrieveConfigurationUseCase(private val mapper: ConfigurationDataMapper,
                                   private val api: MoviesPreviewApiWrapper,
                                   private val configurationCache: MoviesConfigurationCache) : UseCase<Any, MoviesConfiguration> {

    override fun execute(param: Any?): MoviesConfiguration? {
        return if (configurationCache.isMoviesConfigurationOutOfDate()) {
            api.getLastMovieConfiguration()?.let {
                configurationCache.saveMoviesConfig(it)
                mapper.convertMoviesConfigurationFromDataModel(it)
            }
        } else {
            configurationCache.getLastMovieConfiguration()?.let {
                mapper.convertMoviesConfigurationFromDataModel(it)
            }
        }
    }
}