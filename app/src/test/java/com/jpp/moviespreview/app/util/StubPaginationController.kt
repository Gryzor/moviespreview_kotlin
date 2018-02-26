package com.jpp.moviespreview.app.util

import com.jpp.moviespreview.app.ui.UiPage
import com.jpp.moviespreview.app.ui.interactors.PaginationController

/**
 * Stub implementation of [PaginationController] to test interactions.
 *
 * Created by jpp on 2/23/18.
 */
class StubPaginationController : PaginationController {

    companion object {
        const val CALL_END_OF_PAGING = 1
        const val CALL_NEXT_PAGE = 2
    }

    var whatToDo = CALL_NEXT_PAGE
    var nextPage = 1


    override fun controlPagination(getAllPages: () -> List<UiPage>, onEndOfPaging: () -> Unit, onNextPage: (Int) -> Unit) {
        when (whatToDo) {
            CALL_NEXT_PAGE -> onNextPage(nextPage)
            CALL_END_OF_PAGING -> onEndOfPaging()
            else -> throw UnsupportedOperationException()
        }
    }

}