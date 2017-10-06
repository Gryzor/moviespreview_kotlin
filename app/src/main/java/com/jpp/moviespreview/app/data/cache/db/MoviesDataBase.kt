package com.jpp.moviespreview.app.data.cache.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 * ROOM database definition.
 *
 * Created by jpp on 10/6/17.
 */
@Database(entities = arrayOf(ImageConfig::class,
        ImageSize::class,
        Timestamp::class)
        , version = 1)
abstract class MoviesDataBase : RoomDatabase() {
    abstract fun imageConfigDao(): ImageConfigDao
    abstract fun timestampDao(): TimestampDao
}