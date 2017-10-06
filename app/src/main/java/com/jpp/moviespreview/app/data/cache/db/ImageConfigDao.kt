package com.jpp.moviespreview.app.data.cache.db

import android.arch.persistence.room.*

/**
 * DAO definition for ImageConfig data class
 *
 * Created by jpp on 10/6/17.
 */
@Dao
interface ImageConfigDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImageConfig(imageConfig: ImageConfig): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateImageConfig(imageConfig: ImageConfig)

    @Query("select * from image_configuration")
    fun getLastImageConfig(): ImageConfig?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllImageSize(sizes: List<ImageSize>)
}