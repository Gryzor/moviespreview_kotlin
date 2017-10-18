package com.jpp.moviespreview.app.ui

/**
 * Created by jpp on 10/11/17.
 */
class MoviesContext {


    var imageConfig: ImageConfiguration? = null
    var movieGenres: List<MovieGenre>? = null


    fun isConfigCompleted() = imageConfig != null && movieGenres != null

}