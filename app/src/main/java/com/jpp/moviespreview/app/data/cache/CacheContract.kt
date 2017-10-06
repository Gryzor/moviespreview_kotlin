package com.jpp.moviespreview.app.data.cache

import com.jpp.moviespreview.app.data.MoviesConfiguration
import com.jpp.moviespreview.app.data.cache.db.MoviesDataBase
import com.jpp.moviespreview.app.extentions.isOlderThan

/**
 * Created by jpp on 10/6/17.
 */
interface MoviesCache {
    fun saveMoviesConfig(moviesConfig: MoviesConfiguration, updateDate: Long)
    fun isLastConfigOlderThan(timeStamp: Long): Boolean
}


class MoviesCacheImpl(private val cacheDataMapper: CacheDataMapper,
                      private val database: MoviesDataBase) : MoviesCache {


    override fun isLastConfigOlderThan(timeStamp: Long): Boolean {
        val lastConfig = database.imageConfigDao().getLastImageConfig()
        return lastConfig == null || lastConfig.lastUpdate.isOlderThan(timeStamp)
    }


    override fun saveMoviesConfig(moviesConfig: MoviesConfiguration, updateDate: Long) {
        val cacheImageConfig = cacheDataMapper.convertMoviesConfigurationToCacheModel(moviesConfig, updateDate)
        val parentId = database.imageConfigDao().insertImageConfig(cacheImageConfig)

        val imageSizes = cacheDataMapper.convertImagesConfigurationToCacheModel(parentId, moviesConfig)
        database.imageConfigDao().insertAllImageSize(imageSizes)
    }

}