package com.jpp.moviespreview.app.domain.configuration

import com.jpp.moviespreview.app.data.cache.MoviesCache
import com.jpp.moviespreview.app.data.server.MoviesPreviewApiWrapper
import com.jpp.moviespreview.app.domain.MoviesConfiguration
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.extentions.TimeUtils
import java.util.concurrent.TimeUnit

/**
 * Created by jpp on 10/5/17.
 */
class RetrieveConfigurationUseCase(private val mapper: ConfigurationDataMapper,
                                   private val api: MoviesPreviewApiWrapper,
                                   private val cache: MoviesCache,
                                   private val timeUtils: TimeUtils) : UseCase<Any, MoviesConfiguration> {

    override fun execute(param: Any?): MoviesConfiguration? {
        return if (cache.isLastConfigOlderThan(TimeUnit.MINUTES.toMillis(30), timeUtils)) {
            api.getLastMovieConfiguration().let {
                cache.saveMoviesConfig(it, timeUtils.currentTimeInMillis())
                mapper.convertMoviesConfigurationFromDataModel(it)
            }
        } else {
            cache.getLastMovieConfiguration()?.let {
                mapper.convertMoviesConfigurationFromDataModel(it)
            }
        }
    }
}