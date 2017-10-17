package com.jpp.moviespreview.app.data.cache.genre

import com.jpp.moviespreview.app.data.Genre
import com.jpp.moviespreview.app.data.cache.db.MoviesDataBase
import com.jpp.moviespreview.app.data.cache.db.Timestamp
import com.jpp.moviespreview.app.data.cache.db.isTimestampOlderThan
import com.jpp.moviespreview.app.util.TimeUtils

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
    fun saveGenreList(genres: List<Genre>, updateDate: Long)

    /**
     * Determinate if the last genres list is older than the provided [timeStamp]
     */
    fun isLastGenreListOlderThan(timeStamp: Long, timeUtils: TimeUtils): Boolean


    /**
     * Retrieves the last list of genres from the local storage.
     */
    fun getLastGenreList(): List<Genre>?
}


class MoviesGenreCacheImpl(private val cacheDataMapper: MoviesGenreCacheDataMapper,
                           private val database: MoviesDataBase) : MoviesGenreCache {

    companion object {
        val GENRES_TIMESTAMP = Timestamp(2)
    }

    override fun isLastGenreListOlderThan(timeStamp: Long, timeUtils: TimeUtils) =
            database.timestampDao().isTimestampOlderThan(GENRES_TIMESTAMP, timeStamp, timeUtils)

    override fun getLastGenreList(): List<Genre>? {
        return database.genresDao().getAllGenres()?.let {
            cacheDataMapper.convertCacheGenresIntoDataGenres(it)
        }
    }

    override fun saveGenreList(genres: List<Genre>, updateDate: Long) {
        GENRES_TIMESTAMP.lastUpdate = updateDate
        database.timestampDao().insertTimestamp(GENRES_TIMESTAMP)

        database.genresDao().insertAllGenres(cacheDataMapper.convertDataGenresIntoCacheGenres(genres))
    }
}