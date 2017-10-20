package com.jpp.moviespreview.app.data.cache.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

/**
 * DAO definition for the Timestamps
 *
 * Created by jpp on 10/6/17.
 */
@Dao
interface TimestampDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTimestamp(timestamp: Timestamp)

    @Query("select * from timestamps where timestamp_id = :timestampId")
    fun getTimestamp(timestampId: Long): Timestamp?
}