package com.jpp.moviespreview.app.ui

/**
 * Contains the context of the application in terms of
 * what needs to show information to the user.
 *
 * Created by jpp on 10/11/17.
 */
class MoviesContext {


    var imageConfig: List<ImageConfiguration>? = null
    var movieGenres: List<MovieGenre>? = null
    private var moviePages = ArrayList<MoviePage>()


    fun isConfigCompleted(): Boolean {
        var completed = false
        if (imageConfig != null) {
            (imageConfig?.isNotEmpty())
            completed = if (movieGenres != null) {
                movieGenres?.isNotEmpty()!!
            } else {
                false
            }

        }
        return completed
    }


    fun addMoviePage(moviePage: MoviePage) {
        if (moviePages.contains(moviePage)) {
            throw IllegalStateException("Wrong! Your're trying to add an existing page")
        }
        moviePages.add(moviePage)
    }

}