package com.jpp.moviespreview.app

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.domain.Genre
import com.jpp.moviespreview.app.ui.ImageConfiguration
import com.jpp.moviespreview.app.ui.MovieGenre
import com.jpp.moviespreview.app.ui.MoviesContext
import org.mockito.Mockito
import com.jpp.moviespreview.app.data.Movie as DataMovie
import com.jpp.moviespreview.app.data.MoviePage as DataMoviePage
import com.jpp.moviespreview.app.domain.Movie as DomainMovie
import com.jpp.moviespreview.app.domain.MoviePage as DomainMoviePage


/**
 * Mocks a list of [ImageConfiguration].
 */
fun MoviesContext.mockImageConfig() = listOf(
        ImageConfiguration("url", "w92", 92),
        ImageConfiguration("url", "w154", 154),
        ImageConfiguration("url", "w185", 185),
        ImageConfiguration("url", "w342", 342),
        ImageConfiguration("url", "w500", 500),
        ImageConfiguration("url", "w780", 780),
        ImageConfiguration("url", "original", -1)
)


/**
 * Mocks a list of [MovieGenre]
 */
fun MoviesContext.mockMovieGenres() = listOf(
        MovieGenre(1, "genre1", R.drawable.ic_generic),
        MovieGenre(2, "genre2", R.drawable.ic_generic),
        MovieGenre(3, "genre3", R.drawable.ic_generic),
        MovieGenre(4, "genre4", R.drawable.ic_generic)
)

/**
 * Completes the configuration of the context with mocked data.
 */
fun MoviesContext.completeConfig(imageConfigMockList: List<ImageConfiguration> = mockImageConfig(),
                                 movieGenreMockList: List<MovieGenre> = mockMovieGenres()) {
    imageConfig = imageConfigMockList
    movieGenres = movieGenreMockList
}


/**
 * Stub class container to stub methods related to [DomainMoviePage]
 */
class DomainPageStubs {
    companion object
}


/**
 * Stubs a [DomainMoviePage]
 */
fun DomainPageStubs.Companion.stubMoviePage(page: Int, totalPages: Int, totalResults: Int) = DomainMoviePage(page, stubDomainMovieList(), totalPages, totalResults)


/**
 * Mocks a list of [DomainMovie]
 */
fun DomainPageStubs.Companion.stubDomainMovieList() = listOf(
        DomainMovie(1.toDouble(),
                "One",
                "One",
                "OverviewOne",
                "ReleaseDateOne",
                "OriginalLanguage1",
                "PosterPathOne",
                "backdropPathOne",
                listOf(Genre(1, "Genre1")),
                12.toDouble(),
                12F,
                12F),
        DomainMovie(2.toDouble(),
                "Two",
                "Two",
                "OverviewTwo",
                "ReleaseDateTwo",
                "OriginalLanguage2",
                "PosterPathTwo",
                "backdropPathTwo",
                listOf(Genre(2, "Genre2")),
                12.toDouble(),
                12F,
                12F),
        DomainMovie(3.toDouble(),
                "Three",
                "Three",
                "OverviewThree",
                "ReleaseDateThree",
                "OriginalLanguage3",
                "PosterPathThree",
                "backdropPathThree",
                listOf(Genre(3, "Genre3")),
                12.toDouble(),
                12F,
                12F)
)

/**
 * Stub class container to stub methods related to [DataMoviePage]
 */
class DataPageStubs {
    companion object
}


/**
 * Stubs a [DomainMoviePage]
 */
fun DataPageStubs.Companion.stubDataMoviePage(page: Int, totalPages: Int, totalResults: Int) = DataMoviePage(page, stubDataMovieList(), totalPages, totalResults)


/**
 * Mocks a list of [DomainMovie]
 */
fun DataPageStubs.Companion.stubDataMovieList() = listOf(
        DataMovie(1.toDouble(),
                "One",
                "One",
                "OverviewOne",
                "ReleaseDateOne",
                "OriginalLanguage1",
                "PosterPathOne",
                "backdropPathOne",
                listOf(1, 3, 5),
                12.toDouble(),
                12F,
                12F),
        DataMovie(2.toDouble(),
                "Two",
                "Two",
                "OverviewTwo",
                "ReleaseDateTwo",
                "OriginalLanguage2",
                "PosterPathTwo",
                "backdropPathTwo",
                listOf(2, 3, 4),
                12.toDouble(),
                12F,
                12F),
        DataMovie(3.toDouble(),
                "Three",
                "Three",
                "OverviewThree",
                "ReleaseDateThree",
                "OriginalLanguage3",
                "PosterPathThree",
                "backdropPathThree",
                listOf(5, 1, 2),
                12.toDouble(),
                12F,
                12F)
)

/**
 * Mocks a given type.
 */
inline fun <reified T : Any> mock() = Mockito.mock(T::class.java)


/**
 * Helper class to load an object from GSON
 */
inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)!!

