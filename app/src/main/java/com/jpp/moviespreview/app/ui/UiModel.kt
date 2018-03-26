package com.jpp.moviespreview.app.ui

import android.support.annotation.DrawableRes
import android.support.annotation.IntDef
import com.jpp.moviespreview.app.util.AllOpen

/**
 * Represents the configuration of the images from the UI perspective.
 * [baseUrl] represents the base url where the images are located.
 * [size] represents the size of the image.
 */
interface ImageConfiguration {

    val baseUrl: String
    val size: String

    /**
     * Prepares and returns the prepared URL that represents this image configuration.
     */
    fun prepareImageUrl(path: String) = "$baseUrl$size$path"
}


/**
 * Represents the poster images configuration.
 */
data class PosterImageConfiguration(override val baseUrl: String,
                                    override val size: String) : ImageConfiguration

/**
 * Represents the profile images configurations.
 */
data class ProfileImageConfiguration(override val baseUrl: String,
                                     override val size: String) : ImageConfiguration


/**
 * Represents a Movie Genre
 */
data class MovieGenre(val id: Int,
                      val name: String,
                      val icon: Int)


/**
 * Represents a Movie for the UI module.
 */
@AllOpen
data class Movie(var id: Double,
                 var title: String,
                 var originalTitle: String,
                 var overview: String,
                 var releaseDate: String,
                 var originalLanguage: String,
                 val images: List<String>,
                 val genres: List<MovieGenre>,
                 val voteCount: Double,
                 val voteAverage: Float,
                 val popularity: Float) {
    /**
     * Retrieves the movies poster path (located at the zero
     * index of the images list).
     */
    fun getPosterPath() = images[0]
}

interface UiPage {

    fun page(): Int

    fun totalPages(): Int
}


/**
 * Represents a page of Movies for the UI module.
 */
data class MoviePage(val page: Int,
                     val results: List<Movie>,
                     val totalPages: Int,
                     val totalResults: Int) : UiPage {
    override fun page() = page

    override fun totalPages() = totalPages
}


/**
 * Represents a person in the credits list. It might be a character or
 * a crew member.
 */
data class CreditPerson(var profilePath: String,
                        var title: String,
                        var subTitle: String)


/**
 * Represents a page of results of a searchFirstPage retrieved from the backend.
 */
data class MultiSearchPage(val query: String,
                           val page: Int,
                           val results: List<MultiSearchResult>,
                           val totalPages: Int,
                           val totalResults: Int) : UiPage {
    override fun page() = page

    override fun totalPages() = totalPages
}

/**
 * Represents result item in the result of a multi searchFirstPage
 */
data class MultiSearchResult(val id: Double,
                             val imagePath: String,
                             val name: String,
                             @DrawableRes val icon: Int,
                             val hasDetails: Boolean,
                             val movieDetails: Movie?)

/**
 * Represents a License description for the
 * libraries used by the application.
 */
data class License(val id: Int,
                   val name: String,
                   val url: String)


/**
 * Defines the types of errors that can be detected at UI level.
 */
data class Error(@Type val type: Long) {

    companion object {

        @IntDef(NO_CONNECTION, UNKNOWN)
        @Retention(AnnotationRetention.SOURCE)
        annotation class Type

        const val NO_CONNECTION = 0L
        const val UNKNOWN = 1L
    }
}

