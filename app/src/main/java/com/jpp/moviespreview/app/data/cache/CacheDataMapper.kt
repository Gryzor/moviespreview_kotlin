package com.jpp.moviespreview.app.data.cache

import com.jpp.moviespreview.app.data.ImagesConfiguration
import com.jpp.moviespreview.app.data.MoviesConfiguration
import com.jpp.moviespreview.app.data.cache.db.ImageConfig
import com.jpp.moviespreview.app.data.cache.db.ImageSize
import com.jpp.moviespreview.app.data.cache.db.Genre
import com.jpp.moviespreview.app.data.Genre as DataGenre


/**
 * Maps data classes between the cache and the data module
 *
 * Created by jpp on 10/20/17.
 */
class CacheDataMapper {


    /*****************************************************
     ********** MOVIES CONFIGURATION SECTION *************
     *****************************************************/

    /**
     * Converts [MoviesConfiguration] from the data model into the [ImageConfig] cache data model.
     */
    fun convertMoviesConfigurationToCacheModel(moviesConfiguration: MoviesConfiguration) = with(moviesConfiguration) {
        ImageConfig(images.base_url)
    }


    /**
     * Converts from [MoviesConfiguration] into a list of [ImageSize]. The [parentId] param is used to
     * set the [ImageSize.id] param.
     */
    fun convertImagesConfigurationToCacheModel(parentId: Long, moviesConfiguration: MoviesConfiguration) = with(moviesConfiguration) {
        convertImageSizes(parentId, images.poster_sizes)
    }


    /**
     * Inner helper method
     */
    private fun convertImageSizes(parentId: Long, imageSizes: List<String>): List<ImageSize> {
        val dataImageSizes = ArrayList<ImageSize>()
        imageSizes.mapTo(dataImageSizes) { ImageSize(it, parentId) }
        return dataImageSizes
    }


    /**
     * Converts [ImageConfig] and [ImageSize] list into a [MoviesConfiguration] data class.
     */
    fun convertCacheImageConfigurationToDataMoviesConfiguration(cacheImageConfig: ImageConfig, cacheImageSizes: List<ImageSize>): MoviesConfiguration =
            MoviesConfiguration(convertCacheImageSizeToImageDataConfiguration(cacheImageConfig.baseUrl, cacheImageSizes))


    /**
     * Inner helper method
     */
    private fun convertCacheImageSizeToImageDataConfiguration(baseUrl: String, cacheImageSizes: List<ImageSize>): ImagesConfiguration {
        val posterSizes = ArrayList<String>()
        cacheImageSizes.mapTo(posterSizes) { it.size }
        return ImagesConfiguration(baseUrl, posterSizes)
    }


    /*****************************************************
     ********** MOVIES CONFIGURATION SECTION *************
     *****************************************************/


    /**
     * Converts from [Genre] database model into data model [DataGenre]
     */
    fun convertCacheGenresIntoDataGenres(cacheGenres: List<Genre>): List<DataGenre> {
        return cacheGenres.mapTo(ArrayList()) {
            DataGenre(it.id, it.name)
        }
    }

    /**
     * Converts from [DataGenre] data model into [Genre] database model
     */
    fun convertDataGenresIntoCacheGenres(dataGenres: List<DataGenre>): List<Genre> {
        return dataGenres.mapTo(ArrayList()) {
            Genre(it.id, it.name)
        }
    }

}