package com.jpp.moviespreview.app.data.cache.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

/**
 * Dao class implementation for [CastCharacter]
 * Created by jpp on 11/7/17.
 */
@Dao
interface CastCharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCastCharacters(castCharacters: List<CastCharacter>)

    @Query("select * from movie_cast_characters where movie_id = :movieId")
    fun getMovieCastCharacters(movieId: Double): List<CastCharacter>?
}