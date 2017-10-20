package com.jpp.moviespreview.app.data.cache.movies

import com.jpp.moviespreview.app.data.Genre
import com.jpp.moviespreview.app.data.MoviePage
import com.jpp.moviespreview.app.data.cache.CacheDataMapper
import com.jpp.moviespreview.app.data.cache.CacheTimestampUtils
import com.jpp.moviespreview.app.data.cache.db.MoviesDataBase

/**
 * Defines the contract of the Cache used to access all the information related to Movies and
 * Movies pages.
 *
 * Created by jpp on 10/20/17.
 */
interface MoviesCache {

    /**
     * Determinate if the movie [page] is out of date or not.
     */
    fun isMoviePageOutOfDate(page: Int): Boolean

    /**
     * Retrieves the MoviePage identified by the [page] if it's stored
     * in the cache. If not, null is returned.
     * If [genres] is null, the data is restored from the cache.
     */
    fun getMoviePage(page: Int, genres: List<Genre>?): List<MoviePage>?

}


class MoviesCacheImpl(private val mapper: CacheDataMapper,
                      private val database: MoviesDataBase,
                      private val cacheTimestampUtils: CacheTimestampUtils) : MoviesCache {


    override fun isMoviePageOutOfDate(page: Int)
            = cacheTimestampUtils.isMoviePageTimestampOutdated(database.timestampDao(), page)


    override fun getMoviePage(page: Int, genres: List<Genre>?): List<MoviePage>? {

//        if (genres == null) {
//            genres = database.genresDao().getAllGenres()
//        }

        return null
    }

}