package com.jpp.moviespreview.app.data.cache

import com.jpp.moviespreview.app.data.MoviesConfiguration
import com.jpp.moviespreview.app.data.cache.db.MoviesDataBase
import com.jpp.moviespreview.app.data.cache.db.Timestamp
import com.jpp.moviespreview.app.extentions.TimeUtils
import com.jpp.moviespreview.app.extentions.isOlderThan
import kotlin.system.exitProcess

/**
 * Defines the contract of the Cache used by the application. It uses Room to store, update, delete
 * and retrieve data.
 *
 * Created by jpp on 10/6/17.
 */
interface MoviesCache {
    /**
     * Saves the provided movie configuration, using the provided updateDate as timestamp for it.
     */
    fun saveMoviesConfig(moviesConfig: MoviesConfiguration, updateDate: Long)

    /**
     * Determinate if the last movies configuration stored is older than the provided value.
     */
    fun isLastConfigOlderThan(timeStamp: Long, timeUtils: TimeUtils): Boolean


    fun getLastMovieConfiguration(): MoviesConfiguration?
}


class MoviesCacheImpl(private val cacheDataMapper: CacheDataMapper,
                      private val database: MoviesDataBase) : MoviesCache {

    companion object {
        val MOVIES_CONFIGURATION_TIMESTAMP = Timestamp(1)
    }


    override fun getLastMovieConfiguration(): MoviesConfiguration? {
        return database.imageConfigDao().getLastImageConfig()?.let {
            val imageSizes = database.imageConfigDao().getImageSizesForConfig(it.id)
            cacheDataMapper.convertCacheImageConfigurationToDataMoviesConfiguration(it, imageSizes!!)
        }
    }


    override fun isLastConfigOlderThan(timeStamp: Long, timeUtils: TimeUtils): Boolean {
        val lastConfig = database.timestampDao().getTimestamp(MOVIES_CONFIGURATION_TIMESTAMP.id)
        return lastConfig == null || timeUtils.isOlderThan(lastConfig.lastUpdate, timeStamp)
    }


    override fun saveMoviesConfig(moviesConfig: MoviesConfiguration, updateDate: Long) {
        // 1 -> insert timestamp
        MOVIES_CONFIGURATION_TIMESTAMP.lastUpdate = updateDate
        database.timestampDao().insertTimestamp(MOVIES_CONFIGURATION_TIMESTAMP)

        // 2 -> insert images configuration
        val cacheImageConfig = cacheDataMapper.convertMoviesConfigurationToCacheModel(moviesConfig)
        val parentId = database.imageConfigDao().insertImageConfig(cacheImageConfig)

        // 3 -> insert image sizes
        val imageSizes = cacheDataMapper.convertImagesConfigurationToCacheModel(parentId, moviesConfig)
        database.imageConfigDao().insertAllImageSize(imageSizes)
    }

}