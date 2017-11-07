package com.jpp.moviespreview.app.data.cache.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

/**
 * Represents the Timestamps that are used to determinate if the stored data
 * is still valid or not.
 * [secondaryId] represents a secondary identifier, in case that the timestamp id is not enough
 * to identify an entity.
 */
@Entity(tableName = "timestamps",
        primaryKeys = arrayOf("timestamp_id", "secondary_id"))
data class Timestamp(@ColumnInfo(name = "timestamp_id") var id: Long,
                     @ColumnInfo(name = "last_update") var lastUpdate: Long = 0,
                     @ColumnInfo(name = "secondary_id") var secondaryId: Int = 0)

/**
 * Represents the ImagesConfiguration data model class.
 */
@Entity(tableName = "image_configuration")
data class ImageConfig(@ColumnInfo(name = "base_url") var baseUrl: String) {
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

/**
 * Represents a Movie Genre in the database.
 */
@Entity(tableName = "genres")
data class Genre(@PrimaryKey @ColumnInfo(name = "_id") var id: Int,
                 @ColumnInfo(name = "name") var name: String)

/**
 * Represents a Movie Page in the database.
 * Note that the [page] number is used as the primary key.
 * [sectionId] represents the identifier of the section to which the page
 * belongs to. For the moment, the default value is 1 that represents 'now playing in theaters'
 */
@Entity(tableName = "movie_pages")
data class MoviePage(@PrimaryKey @ColumnInfo(name = "_id") var page: Int,
                     @ColumnInfo(name = "totalPages") var totalPages: Int,
                     @ColumnInfo(name = "totalResults") var totalResults: Int,
                     @ColumnInfo(name = "section_id") var sectionId: Int = 1)


/**
 * Represents a Movie in the database.
 * It has a foreign key to the MoviePage to which the movie belongs to.
 */
@Entity(tableName = "movies",
        foreignKeys = arrayOf(ForeignKey(entity = MoviePage::class,
                parentColumns = arrayOf("_id"),
                childColumns = arrayOf("page_id"),
                onDelete = ForeignKey.CASCADE)))
data class Movie(@PrimaryKey @ColumnInfo(name = "_id") var id: Double,
                 @ColumnInfo(name = "title") var title: String,
                 @ColumnInfo(name = "original_title") var originalTile: String,
                 @ColumnInfo(name = "overview") var overview: String,
                 @ColumnInfo(name = "release_date") var releaseDate: String,
                 @ColumnInfo(name = "original_language") var originalLanguage: String,
                 @ColumnInfo(name = "poster_path") var posterPath: String?,
                 @ColumnInfo(name = "backdrop_path") var backdropPath: String?,
                 @ColumnInfo(name = "vote_count") var voteCount: Double,
                 @ColumnInfo(name = "vote_average") var voteAverage: Float,
                 @ColumnInfo(name = "popularity") var popularity: Float,
                 @ColumnInfo(name = "page_id") var pageId: Int)


/**
 * Represents the relation between movies and their genres.
 */
@Entity(tableName = "genres_by_movies",
        foreignKeys = arrayOf(ForeignKey(entity = Genre::class,
                parentColumns = arrayOf("_id"),
                childColumns = arrayOf("genre_id"),
                onDelete = ForeignKey.CASCADE),
                ForeignKey(entity = Movie::class,
                        parentColumns = arrayOf("_id"),
                        childColumns = arrayOf("movie_id"),
                        onDelete = ForeignKey.CASCADE)))
data class GenresByMovies(@ColumnInfo(name = "genre_id") var genreId: Int,
                          @ColumnInfo(name = "movie_id") var movieId: Double) {
    @ColumnInfo(name = "_id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}


/**
 * Represents a character present in a movie cast. We don't store the MovieCredits
 * class in the DB. Instead, we store the movie id on this entity and create the MovieCredits
 * on demand. Also, the movie id is not stored as a foreign key since we don't want to
 * delete (or deal with integrity) at this level.
 */
@Entity(tableName = "movie_cast_characters")
data class CastCharacter(@PrimaryKey @ColumnInfo(name = "_id") var id: Double,
                         @ColumnInfo(name = "character") var character: String,
                         @ColumnInfo(name = "credit_id") var creditId: String,
                         @ColumnInfo(name = "gender") var gender: Int,
                         @ColumnInfo(name = "name") var name: String,
                         @ColumnInfo(name = "order") var order: Int,
                         @ColumnInfo(name = "profile_path") var profilePath: String?,
                         @ColumnInfo(name = "movie_id") var movieId: Double) //-> this represents the ID of the movie to which this cast belongs to.
// We do not store it as a foreign key since we don't want to delete it on CASCADE and we don't want to deal with integrity at this level.

/**
 * Represents a crew person present in a movie cast. We don't store the MovieCredits
 * class in the DB. Instead, we store the movie id on this entity and create the MovieCredits
 * on demand. Also, the movie id is not stored as a foreign key since we don't want to
 * delete (or deal with integrity) at this level.
 */
@Entity(tableName = "movie_crew_person")
data class CrewPerson(@PrimaryKey @ColumnInfo(name = "_id") var id: Double,
                      @ColumnInfo(name = "department") var department: String,
                      @ColumnInfo(name = "gender") var gender: Int,
                      @ColumnInfo(name = "credit_id") var creditId: String,
                      @ColumnInfo(name = "job") var job: String,
                      @ColumnInfo(name = "name") var name: String,
                      @ColumnInfo(name = "profile_path") var profilePath: String?,
                      @ColumnInfo(name = "movie_id") var movieId: Double) //-> this represents the ID of the movie to which this cast belongs to.
// We do not store it as a foreign key since we don't want to delete it on CASCADE and we don't want to deal with integrity at this level.
