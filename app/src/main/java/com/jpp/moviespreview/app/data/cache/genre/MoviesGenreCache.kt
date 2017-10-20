package com.jpp.moviespreview.app.data.cache.genre

import com.jpp.moviespreview.app.data.Genre
import com.jpp.moviespreview.app.data.cache.CacheDataMapper
import com.jpp.moviespreview.app.data.cache.CacheTimestampUtils
import com.jpp.moviespreview.app.data.cache.db.MoviesDataBase

/**
 * Defines the contract of the Cache used by the application to store movies genres.
 * It uses Room to store, update, delete and retrieve data.
 *
 * Created by jpp on 10/17/17.
 */
interface MoviesGenreCache {

    /**
     * Stores the provided [genres] into the local cache.
     */
    fun saveGenreList(genres: List<Genre>)

    /**
     * Determinate if the stored genres list are out of date.
     */
    fun isMoviesGenresOutOfDate(): Boolean


    /**
     * Retrieves the last list of genres from the local storage.
     */
    fun getLastGenreList(): List<Genre>?
}


class MoviesGenreCacheImpl(private val mapper: CacheDataMapper,
                           private val database: MoviesDataBase,
                           private val cacheTimestampUtils: CacheTimestampUtils) : MoviesGenreCache {


    override fun isMoviesGenresOutOfDate() =
            cacheTimestampUtils.isMoviesGenreTimestampOutdated(database.timestampDao())

    override fun getLastGenreList(): List<Genre>? {
        return database.genresDao().getAllGenres()?.let {
            mapper.convertCacheGenresIntoDataGenres(it)
        }
    }

    override fun saveGenreList(genres: List<Genre>) {
        val genresTimestamp = cacheTimestampUtils.createMovieGenresTimestamp()
        database.timestampDao().insertTimestamp(genresTimestamp)

        database.genresDao().insertAllGenres(mapper.convertDataGenresIntoCacheGenres(genres))
    }
}