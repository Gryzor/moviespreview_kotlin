package com.jpp.moviespreview.app.domain.movie.credits

import com.jpp.moviespreview.app.data.cache.MoviesCache
import com.jpp.moviespreview.app.data.server.MoviesPreviewApiWrapper
import com.jpp.moviespreview.app.domain.CommandData
import com.jpp.moviespreview.app.domain.Movie
import com.jpp.moviespreview.app.domain.MovieCredits
import com.jpp.moviespreview.app.mock
import com.nhaarman.mockito_kotlin.verify
import junit.framework.Assert.assertEquals
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import com.jpp.moviespreview.app.data.Movie as DataMovie
import com.jpp.moviespreview.app.data.MovieCredits as DataMovieCredits

/**
 * Created by jpp on 3/9/18.
 */
class RetrieveMovieCreditsCommandTest {

    private lateinit var subject: RetrieveMovieCreditsCommand
    private lateinit var mapper: CreditsDataMapper
    private lateinit var api: MoviesPreviewApiWrapper
    private lateinit var moviesCache: MoviesCache


    @Before
    fun setUp() {
        mapper = mock()
        api = mock()
        moviesCache = mock()

        subject = RetrieveMovieCreditsCommand(mapper, api, moviesCache)
    }


    @Test
    fun execute_whenLastCreditsInCacheAreOutdated_retrievesDataFromApi_andStoresItInCache() {
        val domainMovie: Movie = mock()
        val dataMovie: DataMovie = mock()
        val dataMovieCredits: DataMovieCredits = mock()
        val movieId = 12.toDouble()
        val expectedMovieCredits: MovieCredits = mock()

        `when`(dataMovie.id).thenReturn(movieId)
        `when`(mapper.convertDomainMovieIntoDataMovie(domainMovie)).thenReturn(dataMovie)
        `when`(moviesCache.isMovieCreditsOutOfDate(dataMovie)).thenReturn(true)
        `when`(api.getMovieCredits(movieId)).thenReturn(dataMovieCredits)
        `when`(mapper.convertDataMovieCreditsIntoDomainMovieCredits(dataMovieCredits)).thenReturn(expectedMovieCredits)


        val data = CommandData<MovieCredits>(
                {
                    // no op
                },
                {
                    fail()
                }
        )

        subject.execute(data, domainMovie)

        verify(moviesCache).saveMovieCredits(dataMovieCredits)
        assertEquals(expectedMovieCredits, data.value)
    }


    @Test
    fun execute_whenLastCreditsInCacheAreOutdated_andDataRetrievedFromApiIsNull_reportsFail() {
        val domainMovie: Movie = mock()
        val dataMovie: DataMovie = mock()
        val movieId = 12.toDouble()

        `when`(dataMovie.id).thenReturn(movieId)
        `when`(mapper.convertDomainMovieIntoDataMovie(domainMovie)).thenReturn(dataMovie)
        `when`(moviesCache.isMovieCreditsOutOfDate(dataMovie)).thenReturn(true)
        `when`(api.getMovieCredits(movieId)).thenReturn(null)


        val data = CommandData<MovieCredits>(
                {
                    fail()
                },
                {
                    // no op
                }
        )

        subject.execute(data, domainMovie)

        assertNotNull(data.error)
        assertTrue(data.error is IllegalStateException)
    }


    @Test
    fun execute_whenLastCreditsInCacheAreStillValid_retrievesDataFromCache() {
        val domainMovie: Movie = mock()
        val dataMovie: DataMovie = mock()
        val dataMovieCredits: DataMovieCredits = mock()
        val movieId = 12.toDouble()
        val expectedMovieCredits: MovieCredits = mock()

        `when`(dataMovie.id).thenReturn(movieId)
        `when`(mapper.convertDomainMovieIntoDataMovie(domainMovie)).thenReturn(dataMovie)
        `when`(moviesCache.isMovieCreditsOutOfDate(dataMovie)).thenReturn(false)
        `when`(moviesCache.getMovieCreditForMovie(dataMovie)).thenReturn(dataMovieCredits)
        `when`(mapper.convertDataMovieCreditsIntoDomainMovieCredits(dataMovieCredits)).thenReturn(expectedMovieCredits)

        val data = CommandData<MovieCredits>(
                {
                    // no op
                },
                {
                    fail()
                }
        )

        subject.execute(data, domainMovie)

        assertEquals(expectedMovieCredits, data.value)
    }


    @Test
    fun execute_whenLastCreditsInCacheAreStillValid_andDataRetrievedFromCacheIsNull_reportsFail() {
        val domainMovie: Movie = mock()
        val dataMovie: DataMovie = mock()
        val movieId = 12.toDouble()

        `when`(dataMovie.id).thenReturn(movieId)
        `when`(mapper.convertDomainMovieIntoDataMovie(domainMovie)).thenReturn(dataMovie)
        `when`(moviesCache.isMovieCreditsOutOfDate(dataMovie)).thenReturn(false)
        `when`(moviesCache.getMovieCreditForMovie(dataMovie)).thenReturn(null)


        val data = CommandData<MovieCredits>(
                {
                    fail()
                },
                {
                    // no op
                }
        )

        subject.execute(data, domainMovie)

        assertNotNull(data.error)
        assertTrue(data.error is IllegalStateException)
    }
}