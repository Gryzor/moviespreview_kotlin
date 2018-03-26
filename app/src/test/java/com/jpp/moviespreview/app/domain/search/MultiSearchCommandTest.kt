package com.jpp.moviespreview.app.domain.search

import com.jpp.moviespreview.app.data.server.MoviesPreviewApiWrapper
import com.jpp.moviespreview.app.domain.CommandData
import com.jpp.moviespreview.app.domain.Genre
import com.jpp.moviespreview.app.domain.MultiSearchPage
import com.jpp.moviespreview.app.domain.MultiSearchParam
import com.jpp.moviespreview.app.mock
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import com.jpp.moviespreview.app.data.MultiSearchPage as DataMultiSearchPage

/**
 * Created by jpp on 3/16/18.
 */
class MultiSearchCommandTest {

    private lateinit var mapper: MultiSearchDataMapper
    private lateinit var api: MoviesPreviewApiWrapper
    private lateinit var subject: MultiSearchCommand


    @Before
    fun setUp() {
        mapper = mock()
        api = mock()
        subject = MultiSearchCommand(mapper, api)
    }

    @Test(expected = IllegalArgumentException::class)
    fun execute_withNullParam_throwsException() {
        val data = CommandData<MultiSearchPage>(
                {
                    Assert.fail()
                },
                {
                    Assert.fail()
                }
        )

        subject.execute(data, null)
    }


    @Test
    fun execute_withValidData_callsApi() {
        val listOfGenres: List<Genre> = listOf()
        val param = MultiSearchParam("query", 1, listOfGenres)
        val dataResult: DataMultiSearchPage = mock()
        val domainResult: MultiSearchPage = mock()

        `when`(mapper.convertDataSearchPageIntoDomainSearchResult(dataResult, "query", listOfGenres)).thenReturn(domainResult)
        `when`(api.multiSearch(param.query, param.page)).thenReturn(dataResult)


        val data = CommandData<MultiSearchPage>(
                {
                    // no op
                },
                {
                    fail()
                }
        )

        subject.execute(data, param)

        assertEquals(domainResult, data.value)
    }

    @Test
    fun execute_whenApiFails() {
        val param = MultiSearchParam("query", 1, listOf())
        `when`(api.multiSearch(param.query, param.page)).thenReturn(null)

        val data = CommandData<MultiSearchPage>(
                {
                    fail()
                },
                {
                    // no op
                }
        )

        subject.execute(data, param)

        assertNotNull(data.error)
        assertTrue(data.error is IllegalStateException)
    }
}