package com.jpp.moviespreview.app.data.cache.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.jpp.moviespreview.app.util.AllOpen

/**
 * ROOM database definition.
 *
 * Created by jpp on 10/6/17.
 */
@AllOpen
@Database(entities = arrayOf(ImageConfig::class,
        ImageSize::class,
        Timestamp::class,
        Genre::class)
        , version = 1)
abstract class MoviesDataBase : RoomDatabase() {
    abstract fun imageConfigDao(): ImageConfigDao
    abstract fun timestampDao(): TimestampDao
    abstract fun genresDao(): GenresDao
}