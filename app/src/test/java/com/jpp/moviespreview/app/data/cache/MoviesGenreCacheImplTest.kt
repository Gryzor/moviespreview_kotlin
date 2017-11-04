package com.jpp.moviespreview.app.data.cache

import com.jpp.moviespreview.app.data.cache.CacheDataMapper
import com.jpp.moviespreview.app.data.cache.CacheTimestampUtils
import com.jpp.moviespreview.app.data.cache.MoviesGenreCacheImpl
import com.jpp.moviespreview.app.data.cache.db.Genre
import com.jpp.moviespreview.app.data.cache.db.GenresDao
import com.jpp.moviespreview.app.data.cache.db.MoviesDataBase
import com.jpp.moviespreview.app.data.cache.db.TimestampDao
import com.jpp.moviespreview.app.mock
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`


class MoviesGenreCacheImplTest {

    private lateinit var genresDao: GenresDao
    private lateinit var cacheDataMapper: CacheDataMapper
    private lateinit var database: MoviesDataBase
    private lateinit var subject: MoviesGenreCacheImpl
    private lateinit var timestampDao: TimestampDao
    private lateinit var cacheTimestampUtils: CacheTimestampUtils

    @Before
    fun setUp() {
        database = mock()
        cacheDataMapper = mock()
        cacheTimestampUtils = mock()
        subject = MoviesGenreCacheImpl(cacheDataMapper, database, cacheTimestampUtils)

        genresDao = mock()
        `when`(database.genresDao()).thenReturn(genresDao)

        timestampDao = mock()
        `when`(database.timestampDao()).thenReturn(timestampDao)
    }


    @Test
    fun getLastGenreList_whenNoGenresInDb_returnsNull() {
        `when`(genresDao.getAllGenres()).thenReturn(null)
        val lastGenres = subject.getLastGenreList()
        Assert.assertNull(lastGenres)
    }


    @Test
    fun getLastGenreList_returnLastGenresStored() {
        val expected = ArrayList<Genre>()
        `when`(genresDao.getAllGenres()).thenReturn(expected)
        val actual = subject.getLastGenreList()
        Assert.assertEquals(expected, actual)
    }

}