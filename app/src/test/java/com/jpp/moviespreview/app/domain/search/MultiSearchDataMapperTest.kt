package com.jpp.moviespreview.app.domain.search

import com.jpp.moviespreview.app.domain.MultiSearchResult.Companion.UNKNOWN
import com.jpp.moviespreview.app.domain.movie.MovieDataMapper
import com.jpp.moviespreview.app.mock
import com.jpp.moviespreview.app.util.extension.loadObjectFromJsonFile
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import com.jpp.moviespreview.app.data.Genre as DataGenre
import com.jpp.moviespreview.app.data.Genres as DataGenres
import com.jpp.moviespreview.app.data.MultiSearchPage as DataResultPage
import com.jpp.moviespreview.app.data.MultiSearchResult as DataResult

/**
 * Created by jpp on 1/6/18.
 */
class MultiSearchDataMapperTest {

    private lateinit var movieDataMapper: MovieDataMapper
    private lateinit var subject: MultiSearchDataMapper

    @Before
    fun setUp() {
        movieDataMapper = mock()
        subject = MultiSearchDataMapper(movieDataMapper)
    }


    @Test
    fun convertDataSearchPageIntoDomainSearchResultResultWithValidData() {
        val dataResultPage = loadObjectFromJsonFile<DataResultPage>(MultiSearchDataMapperTest::class.java.classLoader, "data_multi_search_valid.json")
        val expectedQuery = "expectedQuery"
        val result = subject.convertDataSearchPageIntoDomainSearchResult(dataResultPage, expectedQuery, listOf())
        assertEquals(1, result.page)
        assertEquals(41, result.totalResults)
        assertEquals(3, result.totalPages)
        assertEquals(20, result.results.size)
        assertEquals(expectedQuery, result.query)
    }


    @Test
    fun convertDataSearchPageIntoDomainSearchResultResultWithInvalidData() {
        val dataResultPage = loadObjectFromJsonFile<DataResultPage>(MultiSearchDataMapperTest::class.java.classLoader, "data_multi_search_invalid.json")
        val expectedQuery = "expectedQuery"
        val result = subject.convertDataSearchPageIntoDomainSearchResult(dataResultPage, expectedQuery, listOf())
        assertEquals(1, result.page)
        assertEquals(41, result.totalResults)
        assertEquals(3, result.totalPages)
        assertEquals(20, result.results.size)
        val lastResult = result.results[19]
        assertEquals(UNKNOWN, lastResult.mediaType)
        assertEquals(expectedQuery, result.query)
    }

}