package com.jpp.moviespreview.app.data.cache

import com.jpp.moviespreview.app.data.MoviesConfiguration
import com.jpp.moviespreview.app.data.cache.db.ImageConfig as CacheImageConfiguration
import com.jpp.moviespreview.app.data.cache.db.ImageSize as DataImageSize

class CacheDataMapper {


    fun convertMoviesConfigurationToCacheModel(moviesConfiguration: MoviesConfiguration) = with(moviesConfiguration) {
        CacheImageConfiguration(images.base_url)
    }


    fun convertImagesConfigurationToCacheModel(parentId: Long, moviesConfiguration: MoviesConfiguration) = with(moviesConfiguration) {
        convertImageSizes(parentId, images.poster_sizes)
    }


    private fun convertImageSizes(parentId: Long, imageSizes: List<String>): List<DataImageSize> {
        val dataImageSizes = ArrayList<DataImageSize>()
        imageSizes.mapTo(dataImageSizes) { DataImageSize(it, parentId) }
        return dataImageSizes
    }

}

