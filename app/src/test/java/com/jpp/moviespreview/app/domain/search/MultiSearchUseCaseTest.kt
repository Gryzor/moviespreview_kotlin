package com.jpp.moviespreview.app.domain.search

import com.jpp.moviespreview.app.data.server.MoviesPreviewApiWrapper
import com.jpp.moviespreview.app.domain.Genre
import com.jpp.moviespreview.app.domain.MultiSearchPage
import com.jpp.moviespreview.app.domain.MultiSearchParam
import com.jpp.moviespreview.app.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import com.jpp.moviespreview.app.data.MultiSearchPage as DataResult

/**
 * Created by jpp on 1/6/18.
 */
class MultiSearchUseCaseTest {

    private lateinit var subject: MultiSearchUseCase
    private lateinit var mapper: MultiSearchDataMapper
    private lateinit var api: MoviesPreviewApiWrapper

    @Before
    fun setUp() {
        mapper = mock()
        api = mock()

        subject = MultiSearchUseCase(mapper, api)
    }

    @Test(expected = IllegalArgumentException::class)
    fun execute_withNullParam_throwsException() {
        subject.execute(null)
    }

    @Test
    fun execute_withValidData_callsApi() {
        val listOfGenres: List<Genre> = listOf()
        val param = MultiSearchParam("query", 1, listOfGenres)
        val dataResult: DataResult = mock()
        val domainResult: MultiSearchPage = mock()
        `when`(mapper.convertDataSearchPageIntoDomainSearchResult(dataResult, "query", listOfGenres)).thenReturn(domainResult)
        `when`(api.multiSearch(param.query, param.page)).thenReturn(dataResult)
        val result = subject.execute(param)

        assertEquals(domainResult, result)
        verify(api).multiSearch(param.query, param.page)
    }


    @Test
    fun execute_whenApiFails() {
        val param = MultiSearchParam("query", 1, listOf())
        `when`(api.multiSearch(param.query, param.page)).thenReturn(null)
        val result = subject.execute(param)

        assertNull(result)
    }
}