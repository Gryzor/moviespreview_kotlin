package com.jpp.moviespreview.app.data.cache

import com.jpp.moviespreview.app.data.Movie
import com.jpp.moviespreview.app.data.MovieCredits
import com.jpp.moviespreview.app.data.MoviePage
import com.jpp.moviespreview.app.data.cache.db.GenresByMovies
import com.jpp.moviespreview.app.data.cache.db.MoviesDataBase
import com.jpp.moviespreview.app.data.cache.db.Movie as DBMovie
import com.jpp.moviespreview.app.data.cache.db.MoviePage as DBMoviePage

/**
 * Defines the contract of the Cache used to access all the information related to Movies and
 * Movies pages.
 *
 * Created by jpp on 10/20/17.
 */
interface MoviesCache {

    /**
     * Determinate if the movie [page] is out of date or not.
     */
    fun isMoviePageOutOfDate(page: Int): Boolean

    /**
     * Retrieves the MoviePage identified by the [page] if it's stored
     * in the cache. If not, null is returned.
     */
    fun getMoviePage(page: Int): MoviePage?


    /**
     * Stores a [MoviePage] into the local cache.
     */
    fun saveMoviePage(moviePage: MoviePage)

    /**
     * Determinate if the [MovieCredits] that belongs to the provided [movie] is out of
     * date or not.
     */
    fun isMovieCreditsOutOfDate(movie: Movie): Boolean


    /**
     * Retrieves the [MovieCredits] that belongs to the provided [movie]. If there is no
     * one stored, null is returned.
     */
    fun getMovieCreditForMovie(movie: Movie): MovieCredits?

    /**
     * Stores a [MovieCredits] into the cache.
     */
    fun saveMovieCredits(credits: MovieCredits)

}


class MoviesCacheImpl(private val mapper: CacheDataMapper,
                      private val database: MoviesDataBase,
                      private val cacheTimestampUtils: CacheTimestampUtils) : MoviesCache {


    override fun isMoviePageOutOfDate(page: Int)
            = cacheTimestampUtils.isMoviePageTimestampOutdated(database.timestampDao(), page)


    override fun getMoviePage(page: Int): MoviePage? {
        return database.moviesDao().getMoviesPage(page)?.let {
            val movies = getMoviesForPage(it)
            if (movies != null) {
                mapper.convertCacheMoviePageInDataMoviePage(it, movies)
            } else {
                null
            }
        }
    }


    /**
     * Retrieves the list of [Movie]s that belongs to a [DBMoviePage]
     */
    private fun getMoviesForPage(dbMoviePage: DBMoviePage): List<Movie>? {
        return database.moviesDao().getMoviesForPage(dbMoviePage.page)?.mapNotNullTo(ArrayList()) {
            val genresByMovie = database.moviesDao().getGenresForMovie(it.id)
            mapper.convertCacheMovieInDataMovie(it, genresByMovie)
        }
    }


    override fun saveMoviePage(moviePage: MoviePage) {
        // 1 -> insert timestamp
        val currentTimestamp = cacheTimestampUtils.createMoviePageTimestamp(moviePage.page)
        database.timestampDao().insertTimestamp(currentTimestamp)

        // 2 -> insert the page
        val cacheMoviePage = mapper.convertDataMoviesPageIntoCacheMoviePage(moviePage)
        database.moviesDao().insertMoviePage(cacheMoviePage)

        // 3 -> insert each movie and the genres
        val cacheMovies = mapper.convertDataMoviesIntoCacheMovie(moviePage.results, moviePage)
        database.moviesDao().insertMovies(cacheMovies)

        // 4 -> insert genres
        for (movie in moviePage.results) {
            database.moviesDao().insertGenresForMovie(movie.genre_ids.mapTo(ArrayList()) { GenresByMovies(it, movie.id) })
        }
    }


    /*****************
     **** CREDITS ****
     *****************/

    override fun isMovieCreditsOutOfDate(movie: Movie)
            = cacheTimestampUtils.isMovieCreditsTimestampOutdated(database.timestampDao(), movie.id.toInt())

    override fun getMovieCreditForMovie(movie: Movie): MovieCredits? {
        with(movie) {
            val characters = database.castCharacterDao().getMovieCastCharacters(id)
            val crew = database.crewPersonDao().getMovieCrew(id)

            return if (characters != null && crew != null) {
                MovieCredits(id, mapper.convertCacheCharacterIntoDataCharacter(characters), mapper.convertCacheCrewIntoDataCrew(crew))
            } else {
                null
            }
        }
    }

    override fun saveMovieCredits(credits: MovieCredits) {
        with(credits) {
            // 1 -> Insert timestamp
            val currentTimestamp = cacheTimestampUtils.createMovieCreditTimestamp(id.toInt())
            database.timestampDao().insertTimestamp(currentTimestamp)

            // 2  -> save MovieCredits
            val cacheCharacters = mapper.convertDataCharacterIntoCacheCharacter(cast, id)
            val cacheCrew = mapper.convertDataCrewIntoCacheCrew(crew, id)

            database.castCharacterDao().insertCastCharacters(cacheCharacters)
            database.crewPersonDao().insertCrew(cacheCrew)
        }
    }

}