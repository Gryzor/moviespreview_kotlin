package com.jpp.moviespreview.app.data.cache.genre

import com.jpp.moviespreview.app.data.Genre
import com.jpp.moviespreview.app.data.cache.db.MoviesDataBase
import com.jpp.moviespreview.app.data.cache.db.Timestamp
import com.jpp.moviespreview.app.util.TimeUtils

/**
 * Created by jpp on 10/17/17.
 */

interface MoviesGenreCache {

    /**
     * Stores the provided [genres] into the local cache.
     */
    fun saveGenreList(genres: List<Genre>)

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
                           private val dataBase: MoviesDataBase) : MoviesGenreCache {

    companion object {
        val GENRES_TIMESTAMP = Timestamp(2)
    }

    override fun isLastGenreListOlderThan(timeStamp: Long, timeUtils: TimeUtils): Boolean {
        val lastGenreTimestamp = dataBase.timestampDao().getTimestamp(GENRES_TIMESTAMP.id)
        return lastGenreTimestamp == null || timeUtils.isOlderThan(lastGenreTimestamp.lastUpdate, timeStamp)
    }

    override fun getLastGenreList(): List<Genre>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveGenreList(genres: List<Genre>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}