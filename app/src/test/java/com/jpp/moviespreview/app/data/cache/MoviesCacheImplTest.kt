package com.jpp.moviespreview.app.data.cache

import com.jpp.moviespreview.app.data.cache.db.*
import com.jpp.moviespreview.app.mock
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import com.jpp.moviespreview.app.data.cache.db.Movie as DBMovie
import com.jpp.moviespreview.app.data.cache.db.MoviePage as DBMoviePage

/**
 * Created by jpp on 10/20/17.
 */
class MoviesCacheImplTest {

    private lateinit var subject: MoviesCacheImpl
    private lateinit var mapper: CacheDataMapper
    private lateinit var database: MoviesDataBase
    private lateinit var cacheTimestampUtils: CacheTimestampUtils
    private lateinit var timestampDao: TimestampDao
    private lateinit var moviesDao: MoviesDao


    @Before
    fun setUp() {
        mapper = CacheDataMapper()
        database = mock()
        cacheTimestampUtils = mock()
        timestampDao = mock()
        moviesDao = mock()

        subject = MoviesCacheImpl(mapper, database, cacheTimestampUtils)

        `when`(database.timestampDao()).thenReturn(timestampDao)
        `when`(database.moviesDao()).thenReturn(moviesDao)

    }


    @Test
    fun getMoviePage_whenMoviePageIsInDb() {
        val dbMoviePage = DBMoviePage(1, 200, 200)
        val dbMovies = mockDbMovies(dbMoviePage.page)

        `when`(moviesDao.getMoviesPage(1)).thenReturn(dbMoviePage)
        `when`(moviesDao.getMoviesForPage(dbMoviePage.page)).thenReturn(dbMovies)
        for (dbMovie in dbMovies) {
            `when`(moviesDao.getGenresForMovie(dbMovie.id)).thenReturn(mockGenresByMovie(dbMovie))
        }


        val result = subject.getMoviePage(1)

        assertNotNull(result)
        assertEquals(3, result!!.results.size)
        assertEquals(200, result.total_pages)
        assertEquals(200, result.total_results)
    }


    @Test
    fun getMoviePage_whenMoviePageIsNotInDb_returnsNull() {
        `when`(moviesDao.getMoviesPage(1)).thenReturn(null)
        val result = subject.getMoviePage(1)
        assertNull(result)
    }

    @Test
    fun getMoviePage_whenMovieNoMoviesInDb_returnsNull() {
        val dbMoviePage = DBMoviePage(1, 200, 200)
        `when`(moviesDao.getMoviesPage(1)).thenReturn(dbMoviePage)
        `when`(moviesDao.getMoviesForPage(dbMoviePage.page)).thenReturn(null)
        val result = subject.getMoviePage(1)
        assertNull(result)
    }


    private fun mockDbMovies(pageId: Int): List<DBMovie> {
        return listOf(
                DBMovie(1.toDouble(),
                        "Title1",
                        "Title1",
                        "Overview1",
                        "ReleaseDate1",
                        "OriginalLanguage1",
                        "PosterPath1",
                        "BackdropPath1",
                        100.toDouble(),
                        11111F,
                        12121F,
                        pageId),
                DBMovie(2.toDouble(),
                        "Title2",
                        "Title2",
                        "Overview2",
                        "ReleaseDate2",
                        "OriginalLanguage2",
                        "PosterPath2",
                        "BackdropPath2",
                        200.toDouble(),
                        2222F,
                        12121F,
                        pageId),
                DBMovie(3.toDouble(),
                        "Title3",
                        "Title3",
                        "Overview3",
                        "ReleaseDate3",
                        "OriginalLanguage3",
                        "PosterPath3",
                        "BackdropPath3",
                        300.toDouble(),
                        33333F,
                        12121F,
                        pageId)

        )
    }

    private fun mockGenresByMovie(dbMovie: DBMovie): List<GenresByMovies> {
        return listOf(GenresByMovies(12, dbMovie.id),
                GenresByMovies(16, dbMovie.id),
                GenresByMovies(18, dbMovie.id))
    }

}