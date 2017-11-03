package com.jpp.moviespreview.app.domain.movie

import com.jpp.moviespreview.app.domain.Genre
import com.jpp.moviespreview.app.DataPageStubs
import com.jpp.moviespreview.app.stubDataMoviePage
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull

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

        val moviePage = DataPageStubs.Companion.stubDataMoviePage(1, 13, 13)

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