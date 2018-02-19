package com.jpp.moviespreview.app.domain.genre

import com.jpp.moviespreview.app.data.cache.MoviesGenreCache
import com.jpp.moviespreview.app.data.server.MoviesPreviewApiWrapper
import com.jpp.moviespreview.app.domain.Command
import com.jpp.moviespreview.app.domain.CommandData
import com.jpp.moviespreview.app.domain.Genre
import com.jpp.moviespreview.app.data.Genre as DataGenre

/**
 *
 * [Command] executed to retrieve the movie's genres:
 * Verifies if the [genreCache] contains valid information,
 * If not, it retrieves the list of Genres from the [api] and updates the last
 * available data in the cache.
 *
 * Created by jpp on 2/14/18.
 */
class RetrieveGenresCommand(private val mapper: GenreDataMapper,
                            private val api: MoviesPreviewApiWrapper,
                            private val genreCache: MoviesGenreCache) : Command<Any, List<Genre>> {


    override fun execute(data: CommandData<List<Genre>>, param: Any?) {
        retrieveDataGenres()?.let {
            mapper.convertGenreListFromDataModel(it).let {
                data.value = it
            }
        } ?: run {
            data.error = IllegalStateException("Can not retrieve genres at this time")
        }
    }


    private fun retrieveDataGenres(): List<DataGenre>? = with(genreCache) {
        if (isMoviesGenresOutOfDate()) {
            api.getGenres()?.let {
                saveGenreList(it.genres)
                return it.genres
            }
        } else {
            getLastGenreList()
        }

    }
}