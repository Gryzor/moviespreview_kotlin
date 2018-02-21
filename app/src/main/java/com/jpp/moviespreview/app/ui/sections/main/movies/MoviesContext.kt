package com.jpp.moviespreview.app.ui.sections.main.movies

import com.jpp.moviespreview.app.ui.MoviePage

/**
 * Represents the context of a section in the Movies section
 *
 * Created by jpp on 2/19/18.
 */
class MoviesContext {

    private val moviePages by lazy { ArrayList<MoviePage>() }

    /**
     * Retrieves a copy of the [MoviePage]s in the context.
     */
    fun getAllMoviePages(): List<MoviePage> = moviePages.toMutableList()


    /**
     * Adds the provided [moviePage] to the [MoviePage] list in the context.
     */
    fun addMoviePage(moviePage: MoviePage) {
        if (moviePages.contains(moviePage)) {
            throw IllegalStateException("Wrong! Your're trying to add an existing page")
        }
        moviePages.add(moviePage)
    }

}