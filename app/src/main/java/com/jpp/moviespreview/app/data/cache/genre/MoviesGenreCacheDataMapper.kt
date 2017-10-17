package com.jpp.moviespreview.app.data.cache.genre

import com.jpp.moviespreview.app.data.cache.db.Genre
import com.jpp.moviespreview.app.data.Genre as DataGenre

/**
 * Maps genres data classes between the cache and the data module
 *
 * Created by jpp on 10/17/17.
 */
class MoviesGenreCacheDataMapper {

    fun convertCacheGenresIntoDataGenres(cacheGenres: List<Genre>): List<DataGenre> {
        return cacheGenres.mapTo(ArrayList()) {
            DataGenre(it.id, it.name)
        }
    }

    fun convertDataGenresIntoCacheGenres(dataGenres: List<DataGenre>): List<Genre> {
        return dataGenres.mapTo(ArrayList()) {
            Genre(it.id, it.name)
        }
    }

}