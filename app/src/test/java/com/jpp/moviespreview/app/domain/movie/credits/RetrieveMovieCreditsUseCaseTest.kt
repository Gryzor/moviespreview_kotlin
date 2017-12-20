package com.jpp.moviespreview.app.domain.movie.credits

import com.jpp.moviespreview.app.data.cache.MoviesCache
import com.jpp.moviespreview.app.data.server.MoviesPreviewApiWrapper
import com.jpp.moviespreview.app.domain.Movie
import com.jpp.moviespreview.app.domain.MovieCredits
import com.jpp.moviespreview.app.mock
import com.nhaarman.mockito_kotlin.verify
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import com.jpp.moviespreview.app.data.Movie as DataMovie
import com.jpp.moviespreview.app.data.MovieCredits as DataMovieCredits

/**
 * Created by jpp on 11/8/17.
 */
class RetrieveMovieCreditsUseCaseTest {

    private lateinit var subject: RetrieveMovieCreditsUseCase
    private lateinit var mapper: CreditsDataMapper
    private lateinit var api: MoviesPreviewApiWrapper
    private lateinit var moviesCache: MoviesCache


    @Before
    fun setUp() {
        mapper = mock()
        api = mock()
        moviesCache = mock()

        subject = RetrieveMovieCreditsUseCase(mapper, api, moviesCache)
    }


    @Test(expected = IllegalArgumentException::class)
    fun execute_withNullParam_throwsException() {
        subject.execute(null)
    }


    @Test
    fun execute_whenMovieIsOutOfDate_retrievesData_andStoresInCache() {
        val param: Movie = mock()
        val dataMovie: DataMovie = mock()
        val movieId = 1.toDouble()
        val dataMovieCredits: DataMovieCredits = mock()
        val domainMovieCredits: MovieCredits = mock()

        `when`(mapper.convertDomainMovieIntoDataMovie(param)).thenReturn(dataMovie)
        `when`(dataMovie.id).thenReturn(movieId)
        `when`(moviesCache.isMovieCreditsOutOfDate(dataMovie)).thenReturn(true)
        `when`(api.getMovieCredits(movieId)).thenReturn(dataMovieCredits)
        `when`(mapper.convertDataMovieCreditsIntoDomainMovieCredits(dataMovieCredits)).thenReturn(domainMovieCredits)

        val result = subject.execute(param)

        verify(mapper).convertDomainMovieIntoDataMovie(param)
        verify(moviesCache).isMovieCreditsOutOfDate(dataMovie)
        verify(api).getMovieCredits(movieId)
        verify(moviesCache).saveMovieCredits(dataMovieCredits)
        verify(mapper).convertDataMovieCreditsIntoDomainMovieCredits(dataMovieCredits)
        assertNotNull(result)
    }


    @Test
    fun execute_whenMovieIsNotOutOfDate_retrievesDataFromCache() {
        val param: Movie = mock()
        val dataMovie: DataMovie = mock()
        val movieId = 1.toDouble()
        val dataMovieCredits: DataMovieCredits = mock()
        val domainMovieCredits: MovieCredits = mock()

        `when`(mapper.convertDomainMovieIntoDataMovie(param)).thenReturn(dataMovie)
        `when`(dataMovie.id).thenReturn(movieId)
        `when`(moviesCache.isMovieCreditsOutOfDate(dataMovie)).thenReturn(false)
        `when`(moviesCache.getMovieCreditForMovie(dataMovie)).thenReturn(dataMovieCredits)
        `when`(mapper.convertDataMovieCreditsIntoDomainMovieCredits(dataMovieCredits)).thenReturn(domainMovieCredits)

        val result = subject.execute(param)

        verify(mapper).convertDomainMovieIntoDataMovie(param)
        verify(moviesCache).isMovieCreditsOutOfDate(dataMovie)
        verify(moviesCache).getMovieCreditForMovie(dataMovie)
        verify(mapper).convertDataMovieCreditsIntoDomainMovieCredits(dataMovieCredits)
        assertNotNull(result)
    }

    @Test
    fun execute_whenMovieIsOutOfDate_retrievesData_andDataIsNull_returnsNull() {
        val param: Movie = mock()
        val dataMovie: DataMovie = mock()
        val movieId = 1.toDouble()

        `when`(mapper.convertDomainMovieIntoDataMovie(param)).thenReturn(dataMovie)
        `when`(dataMovie.id).thenReturn(movieId)
        `when`(moviesCache.isMovieCreditsOutOfDate(dataMovie)).thenReturn(true)
        `when`(api.getMovieCredits(movieId)).thenReturn(null)

        val result = subject.execute(param)

        assertNull(result)
    }
}