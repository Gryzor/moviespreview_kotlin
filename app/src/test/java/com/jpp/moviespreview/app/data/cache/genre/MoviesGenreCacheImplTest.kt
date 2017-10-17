package com.jpp.moviespreview.app.data.cache.genre

import com.jpp.moviespreview.app.data.cache.db.Genre
import com.jpp.moviespreview.app.data.cache.db.GenresDao
import com.jpp.moviespreview.app.data.cache.db.MoviesDataBase
import com.jpp.moviespreview.app.data.cache.db.TimestampDao
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito


class MoviesGenreCacheImplTest {

    private lateinit var genresDao: GenresDao
    private lateinit var cacheDataMapper: MoviesGenreCacheDataMapper
    private lateinit var database: MoviesDataBase
    private lateinit var subject: MoviesGenreCacheImpl
    private lateinit var timestampDao: TimestampDao

    @Before
    fun setUp() {
        database = Mockito.mock(MoviesDataBase::class.java)
        cacheDataMapper = Mockito.mock(MoviesGenreCacheDataMapper::class.java)
        subject = MoviesGenreCacheImpl(cacheDataMapper, database)

        genresDao = Mockito.mock(GenresDao::class.java)
        Mockito.`when`(database.genresDao()).thenReturn(genresDao)

        timestampDao = Mockito.mock(TimestampDao::class.java)
        Mockito.`when`(database.timestampDao()).thenReturn(timestampDao)
    }


    @Test
    fun getLastGenreList_whenNoGenresInDb_returnsNull() {
        Mockito.`when`(genresDao.getAllGenres()).thenReturn(null)
        val lastGenres = subject.getLastGenreList()
        Assert.assertNull(lastGenres)
    }


    @Test
    fun getLastGenreList_returnLastGenresStored() {
        val expected = ArrayList<Genre>()
        Mockito.`when`(genresDao.getAllGenres()).thenReturn(expected)
        val actual = subject.getLastGenreList()
        Assert.assertEquals(expected, actual)
    }

}