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
    var selectedMovie: Movie? = null
    private var moviePages = ArrayList<MoviePage>()
    private var imagesConfigForSizes = HashMap<Int, ImageConfiguration>()



    /**
     * Determinate if the initial configuration is completed or not.
     */
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


    /**
     * Adds the provided [moviePage] to the [moviePages]
     */
    fun addMoviePage(moviePage: MoviePage) {
        if (moviePages.contains(moviePage)) {
            throw IllegalStateException("Wrong! Your're trying to add an existing page")
        }
        moviePages.add(moviePage)
    }


    /**
     * Determinate if the context has movies loaded currently or not.
     */
    fun hasMoviePages() = moviePages.size > 0


    /**
     * Returns the immutable list of [MoviePage]
     */
    fun getAllMoviePages(): List<MoviePage> = moviePages


    /**
     * Retrieves the [ImageConfiguration] that bests suits with the provided [width]
     */
    fun getImageConfigForScreenWidth(width: Int): ImageConfiguration {
        var imageConfig = imagesConfigForSizes[width]

        if (imageConfig == null) {
            imageConfig = findProperImageConfigForScreenWidth(width)
        }

        return imageConfig
    }


    private fun findProperImageConfigForScreenWidth(width: Int): ImageConfiguration {
        if (imageConfig == null) {
            throw IllegalStateException("Config can not be null at this point")
        }
        return imageConfig!!.firstOrNull { it.realSize != null && it.realSize > width } ?: imageConfig!!.last()
    }

}