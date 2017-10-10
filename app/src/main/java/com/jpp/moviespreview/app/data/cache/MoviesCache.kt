package com.jpp.moviespreview.app.data.cache

import com.jpp.moviespreview.app.data.MoviesConfiguration
import com.jpp.moviespreview.app.data.cache.db.MoviesDataBase
import com.jpp.moviespreview.app.data.cache.db.Timestamp
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
    fun isLastConfigOlderThan(timeStamp: Long): Boolean


    fun getLastMovieConfiguration(): MoviesConfiguration?
}


class MoviesCacheImpl(private val cacheDataMapper: CacheDataMapper,
                      private val database: MoviesDataBase) : MoviesCache {

    override fun getLastMovieConfiguration(): MoviesConfiguration? {
        var config: MoviesConfiguration? = null
        with(database.imageConfigDao()) {
            getLastImageConfig()?.let {
                val imageSizes = getImageSizesForConfig(it.id)
                if (imageSizes != null) {
                    config = cacheDataMapper.convertCacheImageConfigurationToDataMoviesConfiguration(it, imageSizes)
                }
            }
        }
        return config
    }


    companion object {
        val MOVIES_CONFIGURATION_TIMESTAMP = Timestamp(1)
    }


    override fun isLastConfigOlderThan(timeStamp: Long): Boolean {
        val lastConfig = database.timestampDao().getTimestamp(MOVIES_CONFIGURATION_TIMESTAMP.id)
        return lastConfig == null || lastConfig.lastUpdate.isOlderThan(timeStamp)
    }


    override fun saveMoviesConfig(moviesConfig: MoviesConfiguration, updateDate: Long) {
        // 1 - insert
        MOVIES_CONFIGURATION_TIMESTAMP.lastUpdate = updateDate
        database.timestampDao().insertTimestamp(MOVIES_CONFIGURATION_TIMESTAMP)

        val cacheImageConfig = cacheDataMapper.convertMoviesConfigurationToCacheModel(moviesConfig)
        val parentId = database.imageConfigDao().insertImageConfig(cacheImageConfig)

        val imageSizes = cacheDataMapper.convertImagesConfigurationToCacheModel(parentId, moviesConfig)
        database.imageConfigDao().insertAllImageSize(imageSizes)
    }

}