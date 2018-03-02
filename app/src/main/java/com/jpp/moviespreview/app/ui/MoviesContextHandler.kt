package com.jpp.moviespreview.app.ui

/**
 * Handles the context of the application (not the Android Context)
 *
 * Created by jpp on 2/21/18.
 */
interface MoviesContextHandler {

    /**
     * Provides a copy of the list of [MoviePage] in the context.
     */
    fun getAllMoviePages(): List<MoviePage>

    /**
     * True if the context configuration is completed,
     * false any other case.
     */
    fun isConfigCompleted(): Boolean


    /**
     * Retrieves a copy of the [MovieGenre] list in the context.
     */
    fun getMovieGenres(): List<MovieGenre>?


    /**
     * Retrieves a copy of the [PosterImageConfiguration] list in the context.
     */
    fun getPosterImageConfigs(): List<PosterImageConfiguration>?

    /**
     * Adds the provided [moviePage] to the movies context.
     */
    fun addMoviePage(moviePage: MoviePage)


    /**
     * Retrieves the [Movie] that has been selected from the home scree.
     */
    fun getSelectedMovie(): Movie?
}


class MoviesContextHandlerImpl(private val moviesContext: ApplicationMoviesContext) : MoviesContextHandler {

    override fun getAllMoviePages(): List<MoviePage> = moviesContext.getAllMoviePages()

    override fun isConfigCompleted(): Boolean = with(moviesContext) {
        posterImageConfig?.isNotEmpty() ?: false
                && profileImageConfig?.isNotEmpty() ?: false
                && movieGenres?.isNotEmpty() ?: false
    }

    override fun getMovieGenres(): List<MovieGenre>? = moviesContext.movieGenres

    override fun getPosterImageConfigs(): List<PosterImageConfiguration>? = moviesContext.posterImageConfig

    override fun addMoviePage(moviePage: MoviePage) {
        moviesContext.addMoviePage(moviePage)
    }

    override fun getSelectedMovie(): Movie? = moviesContext.selectedMovie
}


