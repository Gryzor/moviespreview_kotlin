package com.jpp.moviespreview.app

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.domain.Genre
import com.jpp.moviespreview.app.ui.MovieGenre
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.PosterImageConfiguration
import com.jpp.moviespreview.app.ui.ProfileImageConfiguration
import org.mockito.Mockito
import com.jpp.moviespreview.app.data.Movie as DataMovie
import com.jpp.moviespreview.app.data.MoviePage as DataMoviePage
import com.jpp.moviespreview.app.domain.Movie as DomainMovie
import com.jpp.moviespreview.app.domain.MoviePage as DomainMoviePage


/**
 * Mocks a list of [PosterImageConfiguration].
 */
fun mockPosterImageConfig() = listOf(
        PosterImageConfiguration("url", "w92"),
        PosterImageConfiguration("url", "w154"),
        PosterImageConfiguration("url", "w185"),
        PosterImageConfiguration("url", "w342"),
        PosterImageConfiguration("url", "w500"),
        PosterImageConfiguration("url", "w780"),
        PosterImageConfiguration("url", "original")
)

/**
 * Mocks a list of [ProfileImageConfiguration].
 */
fun mockProfileImageConfig() = listOf(
        ProfileImageConfiguration("url", "h45"),
        ProfileImageConfiguration("url", "h185"),
        ProfileImageConfiguration("url", "h632"),
        ProfileImageConfiguration("url", "original")
)


/**
 * Mocks a list of [MovieGenre]
 */
fun mockMovieGenres() = listOf(
        MovieGenre(1, "genre1", R.drawable.ic_generic),
        MovieGenre(2, "genre2", R.drawable.ic_generic),
        MovieGenre(3, "genre3", R.drawable.ic_generic),
        MovieGenre(4, "genre4", R.drawable.ic_generic)
)

/**
 * Completes the configuration of the context with mocked data.
 */
fun MoviesContext.completeConfig(posterImageConfigMockList: List<PosterImageConfiguration> = mockPosterImageConfig(),
                                 profileImageConfigurationList: List<ProfileImageConfiguration> = mockProfileImageConfig(),
                                 movieGenreMockList: List<MovieGenre> = mockMovieGenres()) {
    posterImageConfig = posterImageConfigMockList
    profileImageConfig = profileImageConfigurationList
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

