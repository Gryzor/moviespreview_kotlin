package com.jpp.moviespreview.app.domain.genre

import com.jpp.moviespreview.app.data.cache.genre.MoviesGenreCache
import com.jpp.moviespreview.app.data.server.MoviesPreviewApiWrapper
import com.jpp.moviespreview.app.domain.Genre
import com.jpp.moviespreview.app.domain.UseCase

/**
 * Use case executed to retrieve the movie's genres.
 * Verifies if the [genreCache] contains valid information,
 * If not, it retrieves the list of Genres from the [api]
 * Created by jpp on 10/18/17.
 */
class RetrieveGenresUseCase(private val mapper: GenreDataMapper,
                            private val api: MoviesPreviewApiWrapper,
                            private val genreCache: MoviesGenreCache) : UseCase<Any, List<Genre>> {


    override fun execute(param: Any?): List<Genre>? {
        return if (genreCache.isMoviesGenresOutOfDate()) {
            api.getGenres()?.let {
                genreCache.saveGenreList(it.genres)
                mapper.convertGenreListFromDataModel(it.genres)
            }
        } else {
            genreCache.getLastGenreList()?.let {
                mapper.convertGenreListFromDataModel(it)
            }
        }
    }
}