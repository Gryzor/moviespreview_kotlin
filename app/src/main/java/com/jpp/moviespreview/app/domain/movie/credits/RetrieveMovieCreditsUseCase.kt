package com.jpp.moviespreview.app.domain.movie.credits

import com.jpp.moviespreview.app.data.cache.MoviesCache
import com.jpp.moviespreview.app.data.server.MoviesPreviewApiWrapper
import com.jpp.moviespreview.app.domain.Movie
import com.jpp.moviespreview.app.domain.MovieCredits
import com.jpp.moviespreview.app.domain.UseCase

/**
 * Created by jpp on 11/8/17.
 */
class RetrieveMovieCreditsUseCase(private val mapper: CreditsDataMapper,
                                  private val api: MoviesPreviewApiWrapper,
                                  private val moviesCache: MoviesCache) : UseCase<Movie, MovieCredits> {


    override fun execute(param: Movie?): MovieCredits? {
        if (param == null) {
            throw IllegalArgumentException("The provided movie can not be null")
        }

        val dataMovie = mapper.convertDomainMovieIntoDataMovie(param)

        return if (moviesCache.isMovieCreditsOutOfDate(dataMovie)) {
            api.getMovieCredits(dataMovie.id)?.let {
                moviesCache.saveMovieCredits(it)
                mapper.convertDataMovieCreditsIntoDomainMovieCredits(it)
            }
        } else {
            moviesCache.getMovieCreditForMovie(dataMovie)?.let {
                mapper.convertDataMovieCreditsIntoDomainMovieCredits(it)
            }
        }
    }
}