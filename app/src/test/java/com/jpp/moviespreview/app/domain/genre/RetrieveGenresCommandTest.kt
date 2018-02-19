package com.jpp.moviespreview.app.domain.genre

import com.jpp.moviespreview.app.data.cache.MoviesGenreCache
import com.jpp.moviespreview.app.data.server.MoviesPreviewApiWrapper
import com.jpp.moviespreview.app.domain.CommandData
import com.jpp.moviespreview.app.domain.Genre
import com.jpp.moviespreview.app.mock
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import com.jpp.moviespreview.app.data.Genre as DataGenre
import com.jpp.moviespreview.app.data.Genres as DataGenres

/**
 * Created by jpp on 2/14/18.
 */
class RetrieveGenresCommandTest {

    private lateinit var subject: RetrieveGenresCommand
    private lateinit var mapper: GenreDataMapper
    private lateinit var api: MoviesPreviewApiWrapper
    private lateinit var genreCache: MoviesGenreCache


    @Before
    fun setUp() {
        mapper = mock()
        api = mock()
        genreCache = mock()

        subject = RetrieveGenresCommand(mapper, api, genreCache)
    }


    @Test
    fun execute_whenLastGenresAreOld_retrievesDataFromApi_andSavesNewConfig() {
        val expected: List<Genre> = mock()
        val genresList = ArrayList<DataGenre>()
        val genres = DataGenres(genresList)


        `when`(genreCache.isMoviesGenresOutOfDate()).thenReturn(true)
        `when`(api.getGenres()).thenReturn(genres)
        `when`(mapper.convertGenreListFromDataModel(genresList)).thenReturn(expected)

        val data = CommandData<List<Genre>>(
                {
                    // no op
                },
                {
                    fail()
                }
        )

        subject.execute(data)

        verify(genreCache).saveGenreList(genres.genres)
        assertEquals(expected, data.value)
    }

    @Test
    fun execute_whenLastGenresAreOld_andDataRetrievedFromApiIsNull_reportsFail() {
        `when`(genreCache.isMoviesGenresOutOfDate()).thenReturn(true)
        `when`(api.getGenres()).thenReturn(null)

        val data = CommandData<List<Genre>>(
                {
                    fail()
                },
                {
                    // no op
                }
        )

        subject.execute(data)

        assertNotNull(data.error)
        assertTrue(data.error is IllegalStateException)
    }


    @Test
    fun execute_whenLastConfigIsStillValid_retrievesDataFromCache() {
        val expected: List<Genre> = mock()
        val genresList = ArrayList<DataGenre>()

        `when`(genreCache.isMoviesGenresOutOfDate()).thenReturn(false)
        `when`(genreCache.getLastGenreList()).thenReturn(genresList)
        `when`(mapper.convertGenreListFromDataModel(genresList)).thenReturn(expected)

        val data = CommandData<List<Genre>>(
                {
                    // no op
                },
                {
                    fail()
                }
        )

        subject.execute(data)

        assertEquals(expected, data.value)
    }

    @Test
    fun execute_whenLastConfigIsStillValid_andDataRetievedFromCacheIsNull() {
        `when`(genreCache.isMoviesGenresOutOfDate()).thenReturn(false)
        `when`(genreCache.getLastGenreList()).thenReturn(null)

        val data = CommandData<List<Genre>>(
                {
                    fail()
                },
                {
                    // no op
                }
        )

        subject.execute(data)

        assertNotNull(data.error)
        assertTrue(data.error is IllegalStateException)
    }

}