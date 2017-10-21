package com.jpp.moviespreview.app.domain.movie

import com.jpp.moviespreview.app.domain.Genre
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import com.jpp.moviespreview.app.data.MoviePage as DataMoviePage
import com.jpp.moviespreview.app.data.Movie as DataMovie

import org.junit.Test

/**
 * Created by jpp on 10/21/17.
 */
class MovieDataMapperTest {


    @Test
    fun convertDataMoviePageIntoDomainMoviePage() {
        val genreList = listOf(
                Genre(1, "one"),
                Genre(2, "two"),
                Genre(3, "three"),
                Genre(4, "four"),
                Genre(5, "five"))

        val results = listOf(
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


        val moviePage = DataMoviePage(1, results, 13, 13)

        val converted = MovieDataMapper().convertDataMoviePageIntoDomainMoviePage(moviePage, genreList)


        assertNotNull(converted)
        assertEquals(1, converted.page)
        assertEquals(13, converted.totalPages)
        assertEquals(13, converted.totalResults)

        assertEquals(3, converted.results.size)

        val domainMovie1 = converted.results[0]
        assertEquals(1.toDouble(), domainMovie1.id)
        assertEquals("One", domainMovie1.title)
        assertEquals("One", domainMovie1.originalTitle)
        assertEquals("OverviewOne", domainMovie1.overview)
        assertEquals("ReleaseDateOne", domainMovie1.releaseDate)
        assertEquals("OriginalLanguage1", domainMovie1.originalLanguage)
        assertEquals("PosterPathOne", domainMovie1.posterPath)
        assertEquals("backdropPathOne", domainMovie1.backdropPath)
        assertEquals(12.toDouble(), domainMovie1.voteCount)
        assertEquals(12F, domainMovie1.voteAverage)
        assertEquals(12F, domainMovie1.popularity)
        assertEquals(genreList[0], domainMovie1.genres[0])
        assertEquals(genreList[2], domainMovie1.genres[1])
        assertEquals(genreList[4], domainMovie1.genres[2])


        val domainMovie2 = converted.results[1]
        assertEquals(2.toDouble(), domainMovie2.id)
        assertEquals("Two", domainMovie2.title)
        assertEquals("Two", domainMovie2.originalTitle)
        assertEquals("OverviewTwo", domainMovie2.overview)
        assertEquals("ReleaseDateTwo", domainMovie2.releaseDate)
        assertEquals("OriginalLanguage2", domainMovie2.originalLanguage)
        assertEquals("PosterPathTwo", domainMovie2.posterPath)
        assertEquals("backdropPathTwo", domainMovie2.backdropPath)
        assertEquals(12.toDouble(), domainMovie2.voteCount)
        assertEquals(12F, domainMovie2.voteAverage)
        assertEquals(12F, domainMovie2.popularity)
        assertEquals(genreList[1], domainMovie2.genres[0])
        assertEquals(genreList[2], domainMovie2.genres[1])
        assertEquals(genreList[3], domainMovie2.genres[2])


        val domainMovie3 = converted.results[2]
        assertEquals(3.toDouble(), domainMovie3.id)
        assertEquals("Three", domainMovie3.title)
        assertEquals("Three", domainMovie3.originalTitle)
        assertEquals("OverviewThree", domainMovie3.overview)
        assertEquals("ReleaseDateThree", domainMovie3.releaseDate)
        assertEquals("OriginalLanguage3", domainMovie3.originalLanguage)
        assertEquals("PosterPathThree", domainMovie3.posterPath)
        assertEquals("backdropPathThree", domainMovie3.backdropPath)
        assertEquals(12.toDouble(), domainMovie3.voteCount)
        assertEquals(12F, domainMovie3.voteAverage)
        assertEquals(12F, domainMovie3.popularity)
        assertEquals(genreList[0], domainMovie3.genres[0])
        assertEquals(genreList[1], domainMovie3.genres[1])
        assertEquals(genreList[4], domainMovie3.genres[2])
    }


}