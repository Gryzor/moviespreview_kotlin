package com.jpp.moviespreview.app.domain.search

import com.jpp.moviespreview.app.util.extension.loadObjectFromJsonFile
import junit.framework.Assert.assertEquals
import org.junit.Test
import com.jpp.moviespreview.app.data.Genre as DataGenre
import com.jpp.moviespreview.app.data.Genres as DataGenres
import com.jpp.moviespreview.app.data.MultiSearchPage as DataResultPage
import com.jpp.moviespreview.app.data.MultiSearchResult as DataResult
import com.jpp.moviespreview.app.domain.MultiSearchResult.Companion.UNKNWON

/**
 * Created by jpp on 1/6/18.
 */
class MultiSearchDataMapperTest {

    private val subject = MultiSearchDataMapper()


    @Test
    fun convertDataSearchPageIntoDomainSearchResultResultWithValidData() {
        val dataResultPage = loadObjectFromJsonFile<DataResultPage>(MultiSearchDataMapperTest::class.java.classLoader, "data_multi_search_valid.json")
        val result = subject.convertDataSearchPageIntoDomainSearchResult(dataResultPage)
        assertEquals(1, result.page)
        assertEquals(41, result.totalResults)
        assertEquals(3, result.totalPages)
        assertEquals(20, result.results.size)
    }


    @Test
    fun convertDataSearchPageIntoDomainSearchResultResultWithInvalidData() {
        val dataResultPage = loadObjectFromJsonFile<DataResultPage>(MultiSearchDataMapperTest::class.java.classLoader, "data_multi_search_invalid.json")
        val result = subject.convertDataSearchPageIntoDomainSearchResult(dataResultPage)
        assertEquals(1, result.page)
        assertEquals(41, result.totalResults)
        assertEquals(3, result.totalPages)
        assertEquals(20, result.results.size)
        val lastResult = result.results[19]
        assertEquals(UNKNWON, lastResult.mediaType)
    }

}