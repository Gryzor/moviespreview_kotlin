package com.jpp.moviespreview.app.ui.interactors

import com.jpp.moviespreview.app.ui.UiPage

/**
 * Interactor to handle pagination in those views where the feature is required
 *
 * Created by jpp on 1/15/18.
 */
interface PaginationInteractor {

    fun managePagination(getAllPages: () -> List<UiPage>,
                         onEndOfPaging: () -> Unit,
                         onNextPage: (Int) -> Unit)
}

class PaginationInteractorImpl : PaginationInteractor {


    override fun managePagination(getAllPages: () -> List<UiPage>,
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
        }

        onNextPage(nextPage)
    }
}