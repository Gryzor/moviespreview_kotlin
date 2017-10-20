package com.jpp.moviespreview.app.data.cache.configuration

import com.jpp.moviespreview.app.data.MoviesConfiguration
import com.jpp.moviespreview.app.data.cache.CacheTimestampUtils
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
    fun saveMoviesConfig(moviesConfig: MoviesConfiguration)

    /**
     * Determinate if the last movies configuration stored is out of date.
     */
    fun isMoviesConfigurationOutOfDate(): Boolean


    /**
     * Retrieves the last stored MoviesConfiguration
     */
    fun getLastMovieConfiguration(): MoviesConfiguration?
}


class MoviesConfigurationCacheImpl(private val cacheDataMapper: MoviesConfigurationCacheDataMapper,
                                   private val database: MoviesDataBase,
                                   private val cacheTimestampUtils: CacheTimestampUtils) : MoviesConfigurationCache {


    override fun getLastMovieConfiguration(): MoviesConfiguration? {
        return database.imageConfigDao().getLastImageConfig()?.let {
            val imageSizes = database.imageConfigDao().getImageSizesForConfig(it.id)
            cacheDataMapper.convertCacheImageConfigurationToDataMoviesConfiguration(it, imageSizes!!)
        }
    }


    override fun isMoviesConfigurationOutOfDate() = cacheTimestampUtils.isConfigurationTimestampOutdated(database.timestampDao())


    override fun saveMoviesConfig(moviesConfig: MoviesConfiguration) {
        // 1 -> insert timestamp
        val currentTimestamp = cacheTimestampUtils.createMoviesConfigurationTimestamp()
        database.timestampDao().insertTimestamp(currentTimestamp)

        // 2 -> insert images configuration
        val cacheImageConfig = cacheDataMapper.convertMoviesConfigurationToCacheModel(moviesConfig)
        val parentId = database.imageConfigDao().insertImageConfig(cacheImageConfig)

        // 3 -> insert image sizes
        val imageSizes = cacheDataMapper.convertImagesConfigurationToCacheModel(parentId, moviesConfig)
        database.imageConfigDao().insertAllImageSize(imageSizes)
    }

}