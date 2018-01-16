package com.jpp.moviespreview.app.ui.interactors

import com.jpp.moviespreview.app.ui.UiPage
import junit.framework.Assert.assertEquals
import junit.framework.Assert.fail
import org.junit.Test

/**
 * Created by jpp on 1/15/18.
 */
class PaginationInteractorTest {

    private val subject = PaginationInteractorImpl()

    @Test
    fun managePaginationCallNextPage() {
        subject.managePagination(
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
        subject.managePagination(
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
        subject.managePagination(
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