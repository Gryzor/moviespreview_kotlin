package com.jpp.moviespreview.app.data.cache.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

/**
 * Dao class implementation for [CrewPerson]
 * Created by jpp on 11/7/17.
 */
@Dao
interface CrewPersonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCrew(castCharacters: List<CrewPerson>)

    @Query("select * from movie_crew_person where movie_id = :movieId")
    fun getMovieCrew(movieId: Double): List<CrewPerson>?
}