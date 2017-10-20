package com.jpp.moviespreview.app.domain.genre

import com.jpp.moviespreview.app.domain.Genre
import com.jpp.moviespreview.app.data.Genre as DataGenre

/**
 * Map Genre classes between data and domain layer.
 *
 * Created by jpp on 10/18/17.
 */
class GenreDataMapper {

    fun convertGenreListFromDataModel(dataModelGenreList: List<DataGenre>): List<Genre> {
        return dataModelGenreList.mapTo(ArrayList()) {
            Genre(it.id, it.name)
        }
    }

}