package com.jpp.moviespreview.app.domain.movie

import com.jpp.moviespreview.app.data.cache.MoviesCache
import com.jpp.moviespreview.app.data.server.MoviesPreviewApiWrapper
import com.jpp.moviespreview.app.domain.MoviePage
import com.jpp.moviespreview.app.domain.PageParam
import com.jpp.moviespreview.app.mock
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import com.jpp.moviespreview.app.data.MoviePage as DataMoviePage

/**
 * Created by jpp on 10/21/17.
 */
class RetrieveMoviesInTheaterUseCaseTest {

    private lateinit var subject: RetrieveMoviesInTheaterUseCase
    private lateinit var mapper: MovieDataMapper
    private lateinit var api: MoviesPreviewApiWrapper
    private lateinit var moviesCache: MoviesCache


    @Before
    fun setUp() {
        mapper = mock()
        api = mock()
        moviesCache = mock()

        subject = RetrieveMoviesInTheaterUseCase(mapper, api, moviesCache)
    }

    @Test(expected = IllegalArgumentException::class)
    fun execute_withNullParam_throwsException() {
        subject.execute(null)
    }


    @Test
    fun execute_whenDataInCacheIsValid_returnsDataFromCache() {
        val storedData: DataMoviePage = mock()
        `when`(moviesCache.getMoviePage(1)).thenReturn(storedData)
        `when`(moviesCache.isMoviePageOutOfDate(1)).thenReturn(false)
        val param = PageParam(1, listOf())
        val mappedMoviePage: MoviePage = mock()
        `when`(mapper.convertDataMoviePageIntoDomainMoviePage(storedData, param.genres)).thenReturn(mappedMoviePage)

        val result = subject.execute(param)

        verify(moviesCache).getMoviePage(1)
        verify(mapper).convertDataMoviePageIntoDomainMoviePage(storedData, param.genres)
        assertNotNull(result)
        assertEquals(mappedMoviePage, result)
    }


    @Test
    fun execute_whenDataInCacheIsValid_butFails_returnsNull() {
        `when`(moviesCache.getMoviePage(1)).thenReturn(null)
        `when`(moviesCache.isMoviePageOutOfDate(1)).thenReturn(false)
        val param = PageParam(1, listOf())

        val result = subject.execute(param)

        assertNull(result)
    }

    @Test
    fun execute_whenDataInCacheIsOutOfSate_retrievesDataFromApi_andStoresItInCache() {
        val retrievedData: DataMoviePage = mock()
        `when`(api.getNowPlaying(1)).thenReturn(retrievedData)
        `when`(moviesCache.isMoviePageOutOfDate(1)).thenReturn(true)
        val param = PageParam(1, listOf())
        val mappedMoviePage: MoviePage = mock()
        `when`(mapper.convertDataMoviePageIntoDomainMoviePage(retrievedData, param.genres)).thenReturn(mappedMoviePage)

        val result = subject.execute(param)

        verify(moviesCache).saveMoviePage(retrievedData)
        verify(mapper).convertDataMoviePageIntoDomainMoviePage(retrievedData, param.genres)
        assertNotNull(result)
        assertEquals(mappedMoviePage, result)
    }


    @Test
    fun execute_whenDataInCacheIsOutOfSate_retrievesDataFromApi_andDataIsNull_returnsNull() {
        `when`(api.getNowPlaying(1)).thenReturn(null)
        `when`(moviesCache.isMoviePageOutOfDate(1)).thenReturn(true)
        val param = PageParam(1, listOf())

        val result = subject.execute(param)

        assertNull(result)
    }
}