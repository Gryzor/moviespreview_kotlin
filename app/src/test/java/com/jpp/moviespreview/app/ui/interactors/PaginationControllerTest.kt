package com.jpp.moviespreview.app.ui.interactors

import com.jpp.moviespreview.app.ui.UiPage
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

/**
 * Created by jpp on 1/15/18.
 */
class PaginationControllerTest {

    private val subject = PaginationControllerImpl()

    @Test
    fun managePaginationCallNextPage() {
        subject.controlPagination(
                {
                    listOf(TestUiPage(1, 6),
                            TestUiPage(2, 6),
                            TestUiPage(3, 6),
                            TestUiPage(4, 6))
                },
                {
                    fail()
                },
                {
                    assertEquals(5, it)
                }
        )
    }


    @Test
    fun managePaginationWithFirstPage() {
        subject.controlPagination(
                {
                    listOf()
                },
                {
                    fail()
                },
                {
                    assertEquals(1, it)
                }
        )

    }


    @Test
    fun managePaginationCallsEndOfPage() {
        subject.controlPagination(
                {
                    listOf(TestUiPage(1, 6),
                            TestUiPage(2, 6),
                            TestUiPage(3, 6),
                            TestUiPage(4, 6),
                            TestUiPage(5, 6),
                            TestUiPage(6, 6))
                },
                {
                    // test passes
                },
                {
                    fail()
                }
        )

    }


    private data class TestUiPage(val page: Int, val totalPages: Int) : UiPage {
        override fun page() = page
        override fun totalPages() = totalPages
    }

}