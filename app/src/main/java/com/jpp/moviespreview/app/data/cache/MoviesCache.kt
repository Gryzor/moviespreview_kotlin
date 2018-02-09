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


    override fun getMoviePage(page: Int) = with(database) {
        moviesDao().getMoviesPage(page)?.let { cacheMoviePage ->
            getMoviesForPage(cacheMoviePage)?.let {
                mapper.convertCacheMoviePageInDataMoviePage(cacheMoviePage, it)
            }
        }
    }


    /**
     * Retrieves the list of [Movie]s that belongs to a [DBMoviePage]
     */
    private fun getMoviesForPage(dbMoviePage: DBMoviePage): List<Movie>? = with(database) {
        moviesDao().getMoviesForPage(dbMoviePage.page)?.mapNotNullTo(ArrayList()) { movie ->
            database.moviesDao().getGenresForMovie(movie.id).let {
                mapper.convertCacheMovieInDataMovie(movie, it)
            }
        }
    }


    override fun saveMoviePage(moviePage: MoviePage) {
        with(database) {
            // 1 -> insert timestamp
            cacheTimestampUtils.createMoviePageTimestamp(moviePage.page).let {
                database.timestampDao().insertTimestamp(it)
            }
            // 2 -> insert the page
            mapper.convertDataMoviesPageIntoCacheMoviePage(moviePage).let {
                database.moviesDao().insertMoviePage(it)
            }

            // 3 -> insert each movie and the genres
            mapper.convertDataMoviesIntoCacheMovie(moviePage.results, moviePage).let {
                database.moviesDao().insertMovies(it)
            }

            // 4 -> insert genres
            with(moviesDao()) {
                for (movie in moviePage.results) {
                    insertGenresForMovie(movie.genre_ids.mapTo(ArrayList()) { GenresByMovies(it, movie.id) })
                }
            }
        }
    }


    /*****************
     **** CREDITS ****
     *****************/

    override fun isMovieCreditsOutOfDate(movie: Movie)
            = cacheTimestampUtils.isMovieCreditsTimestampOutdated(database.timestampDao(), movie.id.toInt())

    override fun getMovieCreditForMovie(movie: Movie) =
            with(movie) {
                database.castCharacterDao().getMovieCastCharacters(id)?.let { characters ->
                    database.crewPersonDao().getMovieCrew(id)?.let { crew ->
                        MovieCredits(id, mapper.convertCacheCharacterIntoDataCharacter(characters), mapper.convertCacheCrewIntoDataCrew(crew))
                    }
                }
            }

    override fun saveMovieCredits(credits: MovieCredits) {
        with(credits) {
            with(database) {
                // 1 -> Insert timestamp
                cacheTimestampUtils.createMovieCreditTimestamp(id.toInt()).let {
                    database.timestampDao().insertTimestamp(it)
                }

                // 2  -> save MovieCredits
                with(mapper) {
                    convertDataCharacterIntoCacheCharacter(cast, id).let {
                        castCharacterDao().insertCastCharacters(it)
                    }
                    convertDataCrewIntoCacheCrew(crew, id).let {
                        crewPersonDao().insertCrew(it)
                    }

                }
            }
        }
    }

}