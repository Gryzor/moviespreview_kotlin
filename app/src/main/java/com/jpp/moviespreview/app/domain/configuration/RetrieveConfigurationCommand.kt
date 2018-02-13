package com.jpp.moviespreview.app.domain.configuration

import com.jpp.moviespreview.app.data.cache.MoviesConfigurationCache
import com.jpp.moviespreview.app.data.server.MoviesPreviewApiWrapper
import com.jpp.moviespreview.app.domain.Command
import com.jpp.moviespreview.app.domain.CommandData
import com.jpp.moviespreview.app.domain.MoviesConfiguration
import com.jpp.moviespreview.app.data.MoviesConfiguration as DataMoviesConfiguration

/**
 * [Command] executed to retrieve the last available configuration:
 * It will verify if there's a [MoviesConfiguration] instance in the [configurationCache] that it's not 'too old'.
 * If there is not a valid configuration in the [configurationCache], it will retrieve a new one from
 * the [api] and it will store it in the [configurationCache].
 *
 * Created by jpp on 2/12/18.
 */
class RetrieveConfigurationCommand(private val mapper: ConfigurationDataMapper,
                                   private val api: MoviesPreviewApiWrapper,
                                   private val configurationCache: MoviesConfigurationCache)
    : Command<Any, MoviesConfiguration> {


    override fun execute(data: CommandData<MoviesConfiguration>,
                         param: Any?) {

        getLastMovieConfiguration()?.let {
            mapper.convertMoviesConfigurationFromDataModel(it).let {
                data.value = it
            }
        } ?: run {
            data.error = IllegalStateException("Can not retrieve config at this time")
        }
    }


    private fun getLastMovieConfiguration(): DataMoviesConfiguration? = with(configurationCache) {
        if (isMoviesConfigurationOutOfDate()) {
            api.getLastMovieConfiguration()?.let {
                saveMoviesConfig(it)
            }
        } else {
            getLastMovieConfiguration()
        }
    }
}