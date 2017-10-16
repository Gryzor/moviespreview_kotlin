package com.jpp.moviespreview.app.data.cache.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

/**
 * Dao class implementation for Genres
 * Created by jpp on 10/16/17.
 */
@Dao
interface GenresDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenre(genre: Genre): Long

    @Query("select * from genres")
    fun getAllGenres(): List<Genre>?

}
