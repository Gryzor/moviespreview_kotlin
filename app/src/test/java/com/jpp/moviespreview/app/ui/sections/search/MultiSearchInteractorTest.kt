package com.jpp.moviespreview.app.ui.sections.search

import com.jpp.moviespreview.app.domain.Command
import com.jpp.moviespreview.app.domain.CommandData
import com.jpp.moviespreview.app.domain.MultiSearchParam
import com.jpp.moviespreview.app.mock
import com.jpp.moviespreview.app.ui.*
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractor
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import com.jpp.moviespreview.app.domain.Genre as DomainGenre
import com.jpp.moviespreview.app.domain.MultiSearchPage as DomainMultiSearchPage

/**
 * Created by jpp on 3/17/18.
 */
class MultiSearchInteractorTest {

    private lateinit var subject: MultiSearchInteractor

    private lateinit var mapper: DomainToUiDataMapper
    private lateinit var connectivityInteractor: ConnectivityInteractor
    private lateinit var mockDomainGenres: List<DomainGenre>
    private lateinit var posterImageConfiguration: PosterImageConfiguration
    private lateinit var profileImageConfiguration: ProfileImageConfiguration
    private lateinit var uiMovieGenres: List<MovieGenre>
    private lateinit var command: Command<MultiSearchParam, DomainMultiSearchPage>
    private lateinit var searchData: MultiSearchData


    @Before
    fun setUp() {
        mapper = mock()
        connectivityInteractor = mock()
        mockDomainGenres = mock()
        posterImageConfiguration = mock()
        uiMovieGenres = mock()
        command = mock()
        profileImageConfiguration = mock()
        searchData = MultiSearchData()

        subject = MultiSearchInteractorImpl(mapper, connectivityInteractor, command)
    }

    @Test
    fun configureSetsValuesOnlyOnce() {
        val data: MultiSearchData = mock()
        val movieGenres: List<MovieGenre> = mock()
        val posterImageConfig: PosterImageConfiguration = mock()
        val profileImageConfig: ProfileImageConfiguration = mock()

        `when`(mapper.convertUiGenresIntoDomainGenres(movieGenres)).thenReturn(mockDomainGenres)

        //1st
        subject.configure(data, movieGenres, posterImageConfig, profileImageConfig)

        //2nd
        subject.configure(data, movieGenres, posterImageConfig, profileImageConfig)

        // only once
        verify(mapper).convertUiGenresIntoDomainGenres(movieGenres)
    }

    @Test(expected = IllegalStateException::class)
    fun searchWhenNotConfigured() {
        subject.searchPage("query", 1)
    }


    @Test
    fun searchSuccess() {
        configureSubject()
        val pageNumber = 1
        val query = "query"
        val domainSearchPage: DomainMultiSearchPage = mock()
        val expectedSearchPage: MultiSearchPage = mock()

        `when`(mapper.convertDomainResultPageInUiResultPage(domainSearchPage, posterImageConfiguration, profileImageConfiguration, uiMovieGenres))
                .thenReturn(expectedSearchPage)

        doAnswer {
            val param = it.arguments[1] as MultiSearchParam
            assertEquals(query, param.query)
            assertEquals(pageNumber, param.page)
            assertEquals(mockDomainGenres, param.genres)

            val callback = it.arguments[0] as CommandData<DomainMultiSearchPage>
            callback.value = domainSearchPage
        }.`when`(command).execute(any(), any())


        subject.searchPage(query, pageNumber)

        assertEquals(expectedSearchPage, searchData.lastSearchPage)
        assertNull(searchData.error)
    }


    @Test
    fun searchWithErrorNotifiesConnectivityError() {
        configureSubject()
        `when`(connectivityInteractor.isConnectedToNetwork()).thenReturn(false)

        doAnswer {
            val callback = it.arguments[0] as CommandData<DomainMultiSearchPage>
            callback.error = mock()
        }.`when`(command).execute(any(), any())

        subject.searchPage("query", 1)

        assertNull(searchData.lastSearchPage)
        assertNotNull(searchData.error)
        assertEquals(Error.NO_CONNECTION, searchData.error!!.type)
    }


    @Test
    fun searchWithErrorNotifiesUnknownError() {
        configureSubject()
        `when`(connectivityInteractor.isConnectedToNetwork()).thenReturn(true)

        doAnswer {
            val callback = it.arguments[0] as CommandData<DomainMultiSearchPage>
            callback.error = mock()
        }.`when`(command).execute(any(), any())

        subject.searchPage("query", 1)

        assertNull(searchData.lastSearchPage)
        assertNotNull(searchData.error)
        assertEquals(Error.UNKNOWN, searchData.error!!.type)
    }

    private fun configureSubject() {
        `when`(mapper.convertUiGenresIntoDomainGenres(uiMovieGenres)).thenReturn(mockDomainGenres)
        subject.configure(searchData, uiMovieGenres, posterImageConfiguration, profileImageConfiguration)
    }
}