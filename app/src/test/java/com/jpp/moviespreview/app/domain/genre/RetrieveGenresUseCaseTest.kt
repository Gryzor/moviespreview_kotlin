package com.jpp.moviespreview.app.domain.genre

import com.jpp.moviespreview.app.data.Genre
import com.jpp.moviespreview.app.data.Genres
import com.jpp.moviespreview.app.data.cache.genre.MoviesGenreCache
import com.jpp.moviespreview.app.data.server.MoviesPreviewApiWrapper
import com.jpp.moviespreview.app.util.TimeUtils
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
    private lateinit var timeUtils: TimeUtils

    private lateinit var subject: RetrieveGenresUseCase


    @Before
    fun setUp() {
        mapper = mock(GenreDataMapper::class.java)
        api = mock(MoviesPreviewApiWrapper::class.java)
        genreCache = mock(MoviesGenreCache::class.java)
        timeUtils = mock(TimeUtils::class.java)

        subject = RetrieveGenresUseCase(mapper, api, genreCache, timeUtils)
    }


    @Test
    fun execute_whenLastGenresAreOld_retrievesDataFromApi_andSavesNewConfig() {
        `when`(timeUtils.cacheConfigurationRefreshTime()).thenReturn(30)
        `when`(genreCache.isLastGenreListOlderThan(30, timeUtils)).thenReturn(true)
        val genresList = ArrayList<Genre>()
        val genres = Genres(genresList)
        `when`(api.getGenres()).thenReturn(genres)
        val timestamp = 2000L
        `when`(timeUtils.currentTimeInMillis()).thenReturn(timestamp)

        subject.execute()

        verify(genreCache).saveGenreList(genres.genres, timestamp)
        verify(mapper).convertGenreListFromDataModel(genres.genres)
    }

    @Test
    fun execute_whenLastGenresAreOld_andDataRetrievedFromApiIsNull_returnsNull() {
        `when`(timeUtils.cacheConfigurationRefreshTime()).thenReturn(30)
        `when`(genreCache.isLastGenreListOlderThan(30, timeUtils)).thenReturn(true)
        `when`(api.getGenres()).thenReturn(null)

        val result = subject.execute()

        Assert.assertNull(result)
    }


    @Test
    fun execute_whenLastConfigIsStillValid_retrievesDataFromCache() {
        val genresList = ArrayList<Genre>()
        `when`(timeUtils.cacheConfigurationRefreshTime()).thenReturn(30)
        `when`(genreCache.isLastGenreListOlderThan(30, timeUtils)).thenReturn(false)
        `when`(genreCache.getLastGenreList()).thenReturn(genresList)

        subject.execute()

        verify(mapper).convertGenreListFromDataModel(genresList)
    }

    @Test
    fun execute_whenLastConfigIsStillValid_andDataRetievedFromCacheIsNull() {
        `when`(timeUtils.cacheConfigurationRefreshTime()).thenReturn(30)
        `when`(genreCache.isLastGenreListOlderThan(30, timeUtils)).thenReturn(false)
        `when`(genreCache.getLastGenreList()).thenReturn(null)

        val result = subject.execute()

        Assert.assertNull(result)
    }
}