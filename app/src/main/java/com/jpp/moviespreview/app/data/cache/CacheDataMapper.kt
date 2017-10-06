package com.jpp.moviespreview.app.data.cache

import com.jpp.moviespreview.app.data.ImagesConfiguration
import com.jpp.moviespreview.app.data.MoviesConfiguration
import com.jpp.moviespreview.app.data.cache.db.ImageConfig as CacheImageConfiguration
import com.jpp.moviespreview.app.data.cache.db.ImageSize as CacheImageSize

class CacheDataMapper {


    fun convertMoviesConfigurationToCacheModel(moviesConfiguration: MoviesConfiguration) = with(moviesConfiguration) {
        CacheImageConfiguration(images.base_url)
    }


    fun convertImagesConfigurationToCacheModel(parentId: Long, moviesConfiguration: MoviesConfiguration) = with(moviesConfiguration) {
        convertImageSizes(parentId, images.poster_sizes)
    }


    private fun convertImageSizes(parentId: Long, imageSizes: List<String>): List<CacheImageSize> {
        val dataImageSizes = ArrayList<CacheImageSize>()
        imageSizes.mapTo(dataImageSizes) { CacheImageSize(it, parentId) }
        return dataImageSizes
    }


    fun convertCacheImageConfigurationToDataMoviesConfiguration(cacheImageConfig: CacheImageConfiguration, cacheImageSizes: List<CacheImageSize>): MoviesConfiguration =
            MoviesConfiguration(convertCacheImageSizeToImageDataConfiguration(cacheImageConfig.baseUrl, cacheImageSizes))


    private fun convertCacheImageSizeToImageDataConfiguration(baseUrl: String, cacheImageSizes: List<CacheImageSize>): ImagesConfiguration {
        val posterSizes = ArrayList<String>()
        cacheImageSizes.mapTo(posterSizes) { it.size }
        return ImagesConfiguration(baseUrl, posterSizes)
    }

}

