package com.jpp.moviespreview.app.ui.interactors

import com.jpp.moviespreview.app.ui.UiPage

/**
 * Controls the pagination of a large list of [UiPage]s.
 *
 * Created by jpp on 1/15/18.
 */
interface PaginationController {

    fun controlPagination(getAllPages: () -> List<UiPage>,
                          onEndOfPaging: () -> Unit,
                          onNextPage: (Int) -> Unit)
}

class PaginationControllerImpl : PaginationController {


    override fun controlPagination(getAllPages: () -> List<UiPage>,
                                   onEndOfPaging: () -> Unit,
                                   onNextPage: (Int) -> Unit) {
        var lastPageIndex = 0
        var lastPage: UiPage? = null

        if (getAllPages().isNotEmpty()) {
            lastPage = getAllPages().last()
            lastPageIndex = lastPage.page()
        }

        val nextPage = lastPageIndex + 1

        if (lastPage != null && nextPage > lastPage.totalPages()) {
            onEndOfPaging()
            return
        }

        onNextPage(nextPage)
    }
}