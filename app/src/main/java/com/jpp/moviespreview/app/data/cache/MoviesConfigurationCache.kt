package com.jpp.moviespreview.app.data.cache

import com.jpp.moviespreview.app.data.MoviesConfiguration
import com.jpp.moviespreview.app.data.cache.db.MoviesDataBase
import com.jpp.moviespreview.app.util.AllOpen

/**
 * Defines the contract of the Cache used by the application to store movies configurations.
 * It uses Room to store, update, delete and retrieve data.
 *
 * Created by jpp on 10/6/17.
 */
@AllOpen
interface MoviesConfigurationCache {
    /**
     * Saves the provided movie configuration, using the provided updateDate as timestamp for it.
     */
    fun saveMoviesConfig(moviesConfig: MoviesConfiguration): MoviesConfiguration

    /**
     * Determinate if the last movies configuration stored is out of date.
     */
    fun isMoviesConfigurationOutOfDate(): Boolean


    /**
     * Retrieves the last stored MoviesConfiguration
     */
    fun getLastMovieConfiguration(): MoviesConfiguration?
}


class MoviesConfigurationCacheImpl(private val mapper: CacheDataMapper,
                                   private val database: MoviesDataBase,
                                   private val cacheTimestampUtils: CacheTimestampUtils) : MoviesConfigurationCache {


    override fun getLastMovieConfiguration() = with(database) {
        imageConfigDao().getLastImageConfig()?.let {
            imageConfigDao().getImageSizesForConfig(it.id)?.let { imageSizes ->
                mapper.convertCacheImageConfigurationToDataMoviesConfiguration(it, imageSizes)
            }
        }
    }


    override fun isMoviesConfigurationOutOfDate() = cacheTimestampUtils.isConfigurationTimestampOutdated(database.timestampDao())


    override fun saveMoviesConfig(moviesConfig: MoviesConfiguration): MoviesConfiguration {
        with(database) {
            // 1 -> insert timestamp
            cacheTimestampUtils.createMoviesConfigurationTimestamp().let {
                timestampDao().insertTimestamp(it)
            }

            // 2 -> insert images configuration
            mapper.convertMoviesConfigurationToCacheModel(moviesConfig).let {
                imageConfigDao().insertImageConfig(it).let { parentId ->
                    // 3 -> insert image posterSizes
                    mapper.convertImagesConfigurationToCacheModel(parentId, moviesConfig).let {
                        database.imageConfigDao().insertAllImageSize(it)
                    }
                }
            }
        }
        return moviesConfig
    }
}