package com.jpp.moviespreview.app.data.cache.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

/**
 * Dao representation for the Movies
 *
 * Manages the [MoviePage], [Movie] and [GenresByMovies] tables
 *
 * Created by jpp on 10/20/17.
 */
@Dao
interface MoviesDao {
    @Query("select * from movie_pages where _id = :page")
    fun getMoviesPage(page: Int): MoviePage?

    @Query("select * from movies where page_id = :page")
    fun getMoviesForPage(page: Int): List<Movie>?

    @Query("select * from genres_by_movies where movie_id = :movieId")
    fun getGenresForMovie(movieId: Double): List<GenresByMovies>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMoviePage(moviePage: MoviePage): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenresForMovie(genres: List<GenresByMovies>)
}