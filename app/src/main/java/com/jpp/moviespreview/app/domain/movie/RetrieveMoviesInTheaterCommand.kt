package com.jpp.moviespreview.app.domain.movie

import com.jpp.moviespreview.app.data.cache.MoviesCache
import com.jpp.moviespreview.app.data.server.MoviesPreviewApiWrapper
import com.jpp.moviespreview.app.domain.Command
import com.jpp.moviespreview.app.domain.CommandData
import com.jpp.moviespreview.app.domain.MoviePage
import com.jpp.moviespreview.app.domain.PageParam
import com.jpp.moviespreview.app.data.MoviePage as DataMoviePage

/**
 * [Command] executed to retrieve a [MoviePage] of movies that are currently played in theatres.
 * Verifies if the [moviesCache] has the page stored and it's still valid. If it is, it will
 * return that stored data.
 * If it's not valid, it executes the [api] call to retrieve the information from the server.
 *
 * Created by jpp on 2/19/18.
 */
class RetrieveMoviesInTheaterCommand(private val mapper: MovieDataMapper,
                                     private val api: MoviesPreviewApiWrapper,
                                     private val moviesCache: MoviesCache)
    : Command<PageParam, MoviePage> {


    override fun execute(data: CommandData<MoviePage>, param: PageParam?) {
        val page = param?.page ?: throw IllegalArgumentException("The provided param can not be null")
        getMoviePage(page)?.let {
            mapper.convertDataMoviePageIntoDomainMoviePage(it, param.genres).let {
                data.value = it
            }
        } ?: run {
            data.error = IllegalStateException("Can not retrieve movies at this time")
        }
    }

    private fun getMoviePage(page: Int): DataMoviePage? = with(moviesCache) {
        if (isMoviePageOutOfDate(page)) {
            api.getNowPlaying(page)?.let {
                moviesCache.saveMoviePage(it)
                return it
            }
        } else {
            getMoviePage(page)
        }
    }
}