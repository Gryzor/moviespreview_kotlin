package com.jpp.moviespreview.app.domain.movie

import com.jpp.moviespreview.app.data.cache.MoviesCache
import com.jpp.moviespreview.app.data.server.MoviesPreviewApiWrapper
import com.jpp.moviespreview.app.domain.MoviePage
import com.jpp.moviespreview.app.domain.PageParam
import com.jpp.moviespreview.app.domain.UseCase

/**
 * Use case executed to retrieve a [MoviePage] of movies that are currently played in theatres.
 * Verifies if the [moviesCache] has the page stored and it's still valid. If it is, it will
 * return that stored data.
 * If it's not valid, it executes the [api] call to retrieve the information from the server.
 * Created by jpp on 10/21/17.
 */
class RetrieveMoviesInTheaterUseCase(private val mapper: MovieDataMapper,
                                     private val api: MoviesPreviewApiWrapper,
                                     private val moviesCache: MoviesCache) : UseCase<PageParam, MoviePage> {


    override fun execute(param: PageParam?): MoviePage? {
        val page = param?.page ?: throw IllegalArgumentException("The provided param can not be null")
        return if (moviesCache.isMoviePageOutOfDate(page)) {
            api.getNowPlaying(page)?.let {
                moviesCache.saveMoviePage(it)
                mapper.convertDataMoviePageIntoDomainMoviePage(it, param.genres)
            }
        } else {
            moviesCache.getMoviePage(page)?.let {
                mapper.convertDataMoviePageIntoDomainMoviePage(it, param.genres)
            }
        }
    }
}