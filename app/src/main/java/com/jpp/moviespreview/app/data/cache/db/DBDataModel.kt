package com.jpp.moviespreview.app.data.cache.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

/**
 * Represents the ImagesConfiguration data model class.
 * lastUpdate -> the date indicating the last time this value was updated
 */
@Entity(tableName = "image_configuration")
data class ImageConfig(@ColumnInfo(name = "base_url") var baseUrl: String,
                       @ColumnInfo(name = "last_update") var lastUpdate: Long = 0) {
    @ColumnInfo(name = "_id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}


/**
 * Represents the array of image sizes in ImagesConfiguration.
 * Has a foreign key to the ImageConfig definition table.
 */
@Entity(tableName = "image_size",
        foreignKeys = arrayOf(ForeignKey(entity = ImageConfig::class,
                parentColumns = arrayOf("_id"),
                childColumns = arrayOf("id_image_config"),
                onDelete = ForeignKey.CASCADE)))
data class ImageSize(@ColumnInfo(name = "size") val size: String,
                     @ColumnInfo(name = "id_image_config") val imageConfig: Long = 0) {
    @ColumnInfo(name = "_id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}