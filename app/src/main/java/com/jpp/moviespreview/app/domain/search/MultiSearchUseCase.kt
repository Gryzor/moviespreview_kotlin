package com.jpp.moviespreview.app.domain.search

import com.jpp.moviespreview.app.data.server.MoviesPreviewApiWrapper
import com.jpp.moviespreview.app.domain.MultiSearchPage
import com.jpp.moviespreview.app.domain.MultiSearchParam
import com.jpp.moviespreview.app.domain.UseCase

/**
 * Use case executed to retrieve a [MultiSearchPage].
 *
 * Created by jpp on 1/6/18.
 */
class MultiSearchUseCase(private val mapper: MultiSearchDataMapper,
                         private val api: MoviesPreviewApiWrapper) : UseCase<MultiSearchParam, MultiSearchPage> {

    override fun execute(param: MultiSearchParam?): MultiSearchPage? {
        val page = param?.page ?: throw IllegalArgumentException("The provided param can not be null")

        return api.multiSearch(param.query, page)?.let {
            mapper.convertDataSearchPageIntoDomainSearchResult(it, param.query, param.genres)
        }
    }
}