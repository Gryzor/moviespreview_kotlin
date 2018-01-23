package com.jpp.moviespreview.app.ui

/**
 * Contains the context of the application in terms of
 * what needs to show information to the user.
 *
 * Created by jpp on 10/11/17.
 */
class MoviesContext {


    var posterImageConfig: List<PosterImageConfiguration>? = null
    var profileImageConfig: List<ProfileImageConfiguration>? = null
    var movieGenres: List<MovieGenre>? = null
    var selectedMovie: Movie? = null
    var licenses: List<License>? = null
    private var moviePages = ArrayList<MoviePage>()
    private val movieCredits = HashMap<Movie, List<CreditPerson>>()

    /**
     * Determinate if the initial configuration is completed or not.
     */
    fun isConfigCompleted() =
            posterImageConfig?.isNotEmpty() ?: false && profileImageConfig?.isNotEmpty() ?: false && movieGenres?.isNotEmpty() ?: false

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
     * Retrieves the Credits for a specific movie (if there is any)
     */
    fun getCreditsForMovie(movie: Movie): List<CreditPerson>? = movieCredits[movie]

    /**
     * Puts the Credits for the provided Movie
     */
    fun putCreditsForMovie(movie: Movie, credits: List<CreditPerson>) = movieCredits.put(movie, credits)
}