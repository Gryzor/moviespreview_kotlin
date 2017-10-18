package com.jpp.moviespreview.app.ui

/**
 * Contains the context of the application in terms of
 * what needs to show information to the user.
 *
 * Created by jpp on 10/11/17.
 */
class MoviesContext {


    var imageConfig: ImageConfiguration? = null
    var movieGenres: List<MovieGenre>? = null


    fun isConfigCompleted(): Boolean {
        var completed = false
        if (imageConfig != null) {
            (imageConfig?.baseUrl != null
                    && imageConfig?.sizes?.isNotEmpty()!!)

            completed = if (movieGenres != null) {
                movieGenres?.isNotEmpty()!!
            } else {
                false
            }

        }
        return completed

    }

}