package com.jpp.moviespreview.app.domain.genre

import com.jpp.moviespreview.app.data.Genre
import com.jpp.moviespreview.app.data.Genres
import com.jpp.moviespreview.app.data.cache.MoviesGenreCache
import com.jpp.moviespreview.app.data.server.MoviesPreviewApiWrapper
import com.jpp.moviespreview.app.mock
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

/**
 * Created by jpp on 10/18/17.
 */
class RetrieveGenresUseCaseTest {

    private lateinit var mapper: GenreDataMapper
    private lateinit var api: MoviesPreviewApiWrapper
    private lateinit var genreCache: MoviesGenreCache

    private lateinit var subject: RetrieveGenresUseCase


    @Before
    fun setUp() {
        mapper = mock()
        api = mock()
        genreCache = mock()

        subject = RetrieveGenresUseCase(mapper, api, genreCache)
    }


    @Test
    fun execute_whenLastGenresAreOld_retrievesDataFromApi_andSavesNewConfig() {
        `when`(genreCache.isMoviesGenresOutOfDate()).thenReturn(true)
        val genresList = ArrayList<Genre>()
        val genres = Genres(genresList)
        `when`(api.getGenres()).thenReturn(genres)

        subject.execute()

        verify(genreCache).saveGenreList(genres.genres)
        verify(mapper).convertGenreListFromDataModel(genres.genres)
    }

    @Test
    fun execute_whenLastGenresAreOld_andDataRetrievedFromApiIsNull_returnsNull() {
        `when`(genreCache.isMoviesGenresOutOfDate()).thenReturn(true)
        `when`(api.getGenres()).thenReturn(null)

        val result = subject.execute()

        Assert.assertNull(result)
    }


    @Test
    fun execute_whenLastConfigIsStillValid_retrievesDataFromCache() {
        val genresList = ArrayList<Genre>()
        `when`(genreCache.isMoviesGenresOutOfDate()).thenReturn(false)
        `when`(genreCache.getLastGenreList()).thenReturn(genresList)

        subject.execute()

        verify(mapper).convertGenreListFromDataModel(genresList)
    }

    @Test
    fun execute_whenLastConfigIsStillValid_andDataRetievedFromCacheIsNull() {
        `when`(genreCache.isMoviesGenresOutOfDate()).thenReturn(false)
        `when`(genreCache.getLastGenreList()).thenReturn(null)

        val result = subject.execute()

        Assert.assertNull(result)
    }
}