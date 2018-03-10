package com.jpp.moviespreview.app.domain.movie.credits

import com.jpp.moviespreview.app.data.cache.MoviesCache
import com.jpp.moviespreview.app.data.server.MoviesPreviewApiWrapper
import com.jpp.moviespreview.app.domain.Command
import com.jpp.moviespreview.app.domain.CommandData
import com.jpp.moviespreview.app.domain.Movie
import com.jpp.moviespreview.app.domain.MovieCredits
import com.jpp.moviespreview.app.data.Movie as DataMovie
import com.jpp.moviespreview.app.data.MovieCredits as DataMovieCredits

/**
 * [Command] executed to retrieve [MovieCredits] of the [Movie] provided.
 * It verifies that there is no data available in the cache. If there is, it returns
 * the cached data, otherwise it goes to the [api] to get the latest info.
 *
 * Created by jpp on 3/7/18.
 */
class RetrieveMovieCreditsCommand(private val mapper: CreditsDataMapper,
                                  private val api: MoviesPreviewApiWrapper,
                                  private val moviesCache: MoviesCache)
    : Command<Movie, MovieCredits> {

    override fun execute(data: CommandData<MovieCredits>, param: Movie?) {
        if (param == null) {
            throw IllegalArgumentException("The provided movie can not be null")
        }

        getDataMovieCredits(mapper.convertDomainMovieIntoDataMovie(param))?.let {
            mapper.convertDataMovieCreditsIntoDomainMovieCredits(it).let {
                data.value = it
            }
        } ?: run {
            data.error = IllegalStateException("Can not retrieve the movie details at this time")
        }
    }


    private fun getDataMovieCredits(dataMovie: DataMovie): DataMovieCredits? = with(moviesCache) {
        if (isMovieCreditsOutOfDate(dataMovie)) {
            api.getMovieCredits(dataMovie.id)?.let {
                saveMovieCredits(it)
                return it
            }
        } else {
            getMovieCreditForMovie(dataMovie)
        }
    }


}