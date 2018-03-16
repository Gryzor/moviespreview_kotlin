package com.jpp.moviespreview.app.domain.search

import com.jpp.moviespreview.app.data.server.MoviesPreviewApiWrapper
import com.jpp.moviespreview.app.domain.Command
import com.jpp.moviespreview.app.domain.CommandData
import com.jpp.moviespreview.app.domain.MultiSearchPage
import com.jpp.moviespreview.app.domain.MultiSearchParam

/**
 * [Command] executed to perform a multi-search (search any topic related to Movies).
 * It will execute the search with the provided [MultiSearchParam] and will set the
 * obtained result into the provided [CommandData]
 *
 * Created by jpp on 3/16/18.
 */
class MultiSearchCommand(private val mapper: MultiSearchDataMapper,
                         private val api: MoviesPreviewApiWrapper) : Command<MultiSearchParam, MultiSearchPage> {


    override fun execute(data: CommandData<MultiSearchPage>, param: MultiSearchParam?) {
        val page = param?.page
                ?: throw IllegalArgumentException("The provided param can not be null")

        api.multiSearch(param.query, page)?.let {
            data.value = mapper.convertDataSearchPageIntoDomainSearchResult(it, param.query, param.genres)
        } ?: run {
            data.error = IllegalStateException("Can execute search at this moment")
        }
    }

}