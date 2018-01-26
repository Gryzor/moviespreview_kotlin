package com.jpp.moviespreview.app.data.cache

import com.jpp.moviespreview.app.data.Movie
import com.jpp.moviespreview.app.data.MovieCredits
import com.jpp.moviespreview.app.data.MoviePage
import com.jpp.moviespreview.app.data.cache.db.*
import com.jpp.moviespreview.app.mock
import com.jpp.moviespreview.app.util.extension.fuzzyAssert
import com.jpp.moviespreview.app.util.extension.loadObjectFromJsonFile
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.spy
import com.jpp.moviespreview.app.data.cache.db.Movie as DBMovie
import com.jpp.moviespreview.app.data.cache.db.MoviePage as DBMoviePage

/**
 * Tests MoviesCacheImpl and CacheDataMapper
 *
 * Created by jpp on 10/20/17.
 */
class MoviesCacheImplTest {

    private lateinit var subject: MoviesCacheImpl
    private lateinit var mapper: CacheDataMapper
    private lateinit var database: MoviesDataBase
    private lateinit var cacheTimestampUtils: CacheTimestampUtils
    private lateinit var timestampDao: TimestampDao
    private lateinit var moviesDao: MoviesDao
    private lateinit var castCharacterDao: CastCharacterDao
    private lateinit var crewPersonDao: CrewPersonDao


    @Before
    fun setUp() {
        mapper = spy(CacheDataMapper::class.java)
        database = mock()
        cacheTimestampUtils = mock()
        timestampDao = mock()
        moviesDao = mock()
        castCharacterDao = mock()
        crewPersonDao = mock()

        subject = MoviesCacheImpl(mapper, database, cacheTimestampUtils)

        `when`(database.timestampDao()).thenReturn(timestampDao)
        `when`(database.moviesDao()).thenReturn(moviesDao)
        `when`(database.castCharacterDao()).thenReturn(castCharacterDao)
        `when`(database.crewPersonDao()).thenReturn(crewPersonDao)
    }


    @Test
    fun getMoviePage_whenMoviePageIsInDb() {
        //-- prepare
        val dataMoviePage = loadObjectFromJsonFile<MoviePage>(MoviesCacheImplTest::class.java.classLoader, "data_movies_page.json")

        val dbMoviePage = mapper.convertDataMoviesPageIntoCacheMoviePage(dataMoviePage)
        val dbMovies = mapper.convertDataMoviesIntoCacheMovie(dataMoviePage.results, dataMoviePage)

        `when`(moviesDao.getMoviesPage(dataMoviePage.page)).thenReturn(dbMoviePage)
        `when`(moviesDao.getMoviesForPage(dbMoviePage.page)).thenReturn(dbMovies)
        for (dbMovie in dbMovies) {
            `when`(moviesDao.getGenresForMovie(dbMovie.id)).thenReturn(mockGenresByMovie(dbMovie))
        }

        //-- execute
        val result = subject.getMoviePage(dataMoviePage.page)

        //-- verify
        assertNotNull(result)
        assertEquals(dataMoviePage.results.size, result!!.results.size)
        assertEquals(dataMoviePage.total_pages, result.total_pages)
        assertEquals(dataMoviePage.total_results, result.total_results)
    }


    @Test
    fun getMoviePage_whenMoviePageIsNotInDb_returnsNull() {
        //-- prepare
        `when`(moviesDao.getMoviesPage(1)).thenReturn(null)
        //-- execute
        val result = subject.getMoviePage(1)
        //-- verify
        assertNull(result)
    }

    @Test
    fun getMoviePage_whenMovieNoMoviesInDb_returnsNull() {
        //-- prepare
        val dbMoviePage = DBMoviePage(1, 200, 200)
        `when`(moviesDao.getMoviesPage(1)).thenReturn(dbMoviePage)
        `when`(moviesDao.getMoviesForPage(dbMoviePage.page)).thenReturn(null)
        //-- execute
        val result = subject.getMoviePage(1)
        //-- verify
        assertNull(result)
    }


    @Test
    fun getMovieCreditForMovie_whenIsStoredInDB() {
        // -- prepare
        val dataMovieCredit = loadObjectFromJsonFile<MovieCredits>(MoviesCacheImplTest::class.java.classLoader, "data_movie_credits.json")
        val cacheCharacters = mapper.convertDataCharacterIntoCacheCharacter(dataMovieCredit.cast, dataMovieCredit.id)
        val cacheCrew = mapper.convertDataCrewIntoCacheCrew(dataMovieCredit.crew, dataMovieCredit.id)

        `when`(castCharacterDao.getMovieCastCharacters(dataMovieCredit.id)).thenReturn(cacheCharacters)
        `when`(crewPersonDao.getMovieCrew(dataMovieCredit.id)).thenReturn(cacheCrew)
        val movie: Movie = mock()
        `when`(movie.id).thenReturn(dataMovieCredit.id)

        // -- execute
        val restoredCredits = subject.getMovieCreditForMovie(movie)

        // -- verify
        assertNotNull(restoredCredits)
        fuzzyAssert(dataMovieCredit.id, restoredCredits!!.id)
        assertEquals(dataMovieCredit.cast.size, restoredCredits.cast.size)
        assertEquals(dataMovieCredit.crew.size, restoredCredits.crew.size)
    }


    @Test
    fun getMovieCreditForMovie_whenIsNotStoredInDB_returnsNull() {
        // -- prepare
        val dataMovieCreditId = 1.toDouble()

        `when`(castCharacterDao.getMovieCastCharacters(dataMovieCreditId)).thenReturn(null)
        `when`(crewPersonDao.getMovieCrew(dataMovieCreditId)).thenReturn(null)
        val movie: Movie = mock()
        `when`(movie.id).thenReturn(dataMovieCreditId)

        // -- execute
        val restoredCredits = subject.getMovieCreditForMovie(movie)

        // -- verify
        assertNull(restoredCredits)
    }


    @Test
    fun saveMovieCredits() {
        // -- prepare
        val dataMovieCredit = loadObjectFromJsonFile<MovieCredits>(MoviesCacheImplTest::class.java.classLoader, "data_movie_credits.json")
        val currentTimestamp: Timestamp = mock()
        `when`(cacheTimestampUtils.createMovieCreditTimestamp(dataMovieCredit.id.toInt())).thenReturn(currentTimestamp)
        val cacheCharacters: List<CastCharacter> = mock()
        val cacheCrew: List<CrewPerson> = mock()
        `when`(mapper.convertDataCharacterIntoCacheCharacter(dataMovieCredit.cast, dataMovieCredit.id)).thenReturn(cacheCharacters)
        `when`(mapper.convertDataCrewIntoCacheCrew(dataMovieCredit.crew, dataMovieCredit.id)).thenReturn(cacheCrew)

        // -- execute
        subject.saveMovieCredits(dataMovieCredit)

        // -- verify
        verify(timestampDao).insertTimestamp(currentTimestamp)
        verify(castCharacterDao).insertCastCharacters(cacheCharacters)
        verify(crewPersonDao).insertCrew(cacheCrew)
    }


    private fun mockGenresByMovie(dbMovie: DBMovie): List<GenresByMovies> {
        return listOf(GenresByMovies(12, dbMovie.id),
                GenresByMovies(16, dbMovie.id),
                GenresByMovies(18, dbMovie.id))
    }
}