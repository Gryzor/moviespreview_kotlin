package com.jpp.moviespreview.app.domain.movie

import com.jpp.moviespreview.app.data.cache.MoviesCache
import com.jpp.moviespreview.app.data.server.MoviesPreviewApiWrapper
import com.jpp.moviespreview.app.domain.CommandData
import com.jpp.moviespreview.app.domain.Genre
import com.jpp.moviespreview.app.domain.MoviePage
import com.jpp.moviespreview.app.domain.PageParam
import com.jpp.moviespreview.app.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import com.jpp.moviespreview.app.data.MoviePage as DataMoviePage

/**
 * Created by jpp on 2/19/18.
 */
class RetrieveMoviesInTheaterCommandTest {

    private lateinit var mapper: MovieDataMapper
    private lateinit var api: MoviesPreviewApiWrapper
    private lateinit var moviesCache: MoviesCache
    private lateinit var subject: RetrieveMoviesInTheaterCommand


    @Before
    fun setUp() {
        mapper = mock()
        api = mock()
        moviesCache = mock()
        subject = RetrieveMoviesInTheaterCommand(mapper, api, moviesCache)
    }


    @Test(expected = IllegalArgumentException::class)
    fun execute_withNullParam_throwsException() {
        val data = CommandData<MoviePage>(
                {
                    fail()
                },
                {
                    fail()
                }
        )

        subject.execute(data, null)
    }

    @Test
    fun execute_whenDataInCacheIsValid_returnsDataFromCache() {
        val expectedPage = 1
        val mockGenres: List<Genre> = mock()
        val pageParam = PageParam(expectedPage, mockGenres)
        val dataMoviePage: DataMoviePage = mock()
        val expectedMoviePage: MoviePage = mock()

        `when`(moviesCache.isMoviePageOutOfDate(expectedPage)).thenReturn(false)
        `when`(moviesCache.getMoviePage(expectedPage)).thenReturn(dataMoviePage)
        `when`(mapper.convertDataMoviePageIntoDomainMoviePage(dataMoviePage, mockGenres)).thenReturn(expectedMoviePage)

        val data = CommandData<MoviePage>(
                {
                    //no op
                },
                {
                    fail()
                }
        )

        subject.execute(data, pageParam)

        assertEquals(expectedMoviePage, data.value)
        verifyZeroInteractions(api)
    }

    @Test
    fun execute_whenDataInCacheIsValid_butFails_notifiesException() {
        val expectedPage = 1
        val mockGenres: List<Genre> = mock()
        val pageParam = PageParam(expectedPage, mockGenres)

        `when`(moviesCache.isMoviePageOutOfDate(expectedPage)).thenReturn(false)
        `when`(moviesCache.getMoviePage(expectedPage)).thenReturn(null)

        val data = CommandData<MoviePage>(
                {
                    fail()
                },
                {
                    //no op
                }
        )

        subject.execute(data, pageParam)

        assertNotNull(data.error)
        assertTrue(data.error is IllegalStateException)
    }

    @Test
    fun execute_whenDataInCacheIsOutOfSate_retrievesDataFromApi_andStoresItInCache() {
        val expectedPage = 1
        val mockGenres: List<Genre> = mock()
        val pageParam = PageParam(expectedPage, mockGenres)
        val dataMoviePage: DataMoviePage = mock()
        val expectedMoviePage: MoviePage = mock()


        `when`(moviesCache.isMoviePageOutOfDate(expectedPage)).thenReturn(true)
        `when`(api.getNowPlaying(expectedPage)).thenReturn(dataMoviePage)
        `when`(mapper.convertDataMoviePageIntoDomainMoviePage(dataMoviePage, mockGenres)).thenReturn(expectedMoviePage)


        val data = CommandData<MoviePage>(
                {
                    //no op
                },
                {
                    fail()
                }
        )

        subject.execute(data, pageParam)

        assertEquals(expectedMoviePage, data.value)
        verify(api).getNowPlaying(expectedPage)
    }

    @Test
    fun execute_whenDataInCacheIsOutOfSate_retrievesDataFromApi_andDataIsNull_notifiesException() {
        val expectedPage = 1
        val mockGenres: List<Genre> = mock()
        val pageParam = PageParam(expectedPage, mockGenres)

        `when`(moviesCache.isMoviePageOutOfDate(expectedPage)).thenReturn(true)
        `when`(api.getNowPlaying(expectedPage)).thenReturn(null)

        val data = CommandData<MoviePage>(
                {
                    fail()
                },
                {
                    //no op
                }
        )

        subject.execute(data, pageParam)

        assertNotNull(data.error)
        assertTrue(data.error is IllegalStateException)
        verify(api).getNowPlaying(expectedPage)
    }

}