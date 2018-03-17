package com.jpp.moviespreview.app.ui.sections.main.movies

import com.jpp.moviespreview.app.domain.Command
import com.jpp.moviespreview.app.domain.CommandData
import com.jpp.moviespreview.app.domain.PageParam
import com.jpp.moviespreview.app.mock
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.Error
import com.jpp.moviespreview.app.ui.MovieGenre
import com.jpp.moviespreview.app.ui.MoviePage
import com.jpp.moviespreview.app.ui.PosterImageConfiguration
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractor
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import com.jpp.moviespreview.app.domain.Genre as DomainGenre
import com.jpp.moviespreview.app.domain.MoviePage as DomainMoviePage

/**
 * Created by jpp on 2/21/18.
 */
class MoviesPresenterInteractorTest {

    private lateinit var subject: MoviesPresenterInteractor
    private lateinit var mapper: DomainToUiDataMapper
    private lateinit var connectivityInteractor: ConnectivityInteractor
    private lateinit var retrieveMoviePageCommand: Command<PageParam, DomainMoviePage>
    private lateinit var mockDomainGenres: List<DomainGenre>
    private lateinit var posterImageConfiguration: PosterImageConfiguration
    private lateinit var uiMovieGenres: List<MovieGenre>
    private lateinit var data: MoviesData


    @Before
    fun setUp() {
        mapper = mock()
        connectivityInteractor = mock()
        retrieveMoviePageCommand = mock()
        mockDomainGenres = mock()
        posterImageConfiguration = mock()
        uiMovieGenres = mock()
        data = MoviesData()

        subject = MoviesPresenterInteractorImpl(mapper, connectivityInteractor, retrieveMoviePageCommand)
    }


    @Test
    fun configureSetsValuesOnlyOnce() {
        val data: MoviesData = mock()
        val movieGenres: List<MovieGenre> = mock()
        val posterImageConfiguration: PosterImageConfiguration = mock()
        val mockDomainGenres: List<DomainGenre> = mock()

        `when`(mapper.convertUiGenresIntoDomainGenres(movieGenres)).thenReturn(mockDomainGenres)


        //1st
        subject.configure(data, movieGenres, posterImageConfiguration)

        //2nd
        subject.configure(data, movieGenres, posterImageConfiguration)

        // only once
        verify(mapper).convertUiGenresIntoDomainGenres(movieGenres)
    }


    @Test(expected = IllegalStateException::class)
    fun retrieveMoviePageNotConfigured() {
        subject.retrieveMoviePage(1)
    }


    @Test
    fun retrieveMoviePageSuccess() {
        configureSubject()
        val domainPage: DomainMoviePage = mock()
        val pageNumber = 1
        val expectedMoviePage: MoviePage = mock()

        `when`(mapper.convertDomainMoviePageToUiMoviePage(domainPage, posterImageConfiguration, uiMovieGenres))
                .thenReturn(expectedMoviePage)

        doAnswer {
            val param = it.arguments[1] as PageParam
            assertEquals(pageNumber, param.page)
            assertEquals(mockDomainGenres, param.genres)

            val callback = it.arguments[0] as CommandData<DomainMoviePage>
            callback.value = domainPage
        }.`when`(retrieveMoviePageCommand).execute(any(), any())

        subject.retrieveMoviePage(1)

        assertEquals(expectedMoviePage, data.lastMoviePage)
        assertNull(data.error)
    }


    @Test
    fun retrieveMoviePageNotifiesConnectivityError() {
        configureSubject()
        `when`(connectivityInteractor.isConnectedToNetwork()).thenReturn(false)

        doAnswer {
            val callback = it.arguments[0] as CommandData<DomainMoviePage>
            callback.error = mock() //type not important
        }.`when`(retrieveMoviePageCommand).execute(any(), any())

        subject.retrieveMoviePage(1)

        assertNull(data.lastMoviePage)
        assertNotNull(data.error)
        assertEquals(Error.NO_CONNECTION, data.error!!.type)
    }


    @Test
    fun retrieveMoviePageNotifiesUnknownError() {
        configureSubject()
        `when`(connectivityInteractor.isConnectedToNetwork()).thenReturn(true)

        doAnswer {
            val callback = it.arguments[0] as CommandData<DomainMoviePage>
            callback.error = mock() //type not important
        }.`when`(retrieveMoviePageCommand).execute(any(), any())

        subject.retrieveMoviePage(1)

        assertNull(data.lastMoviePage)
        assertNotNull(data.error)
        assertEquals(Error.UNKNOWN, data.error!!.type)
    }

    private fun configureSubject() {
        `when`(mapper.convertUiGenresIntoDomainGenres(uiMovieGenres)).thenReturn(mockDomainGenres)
        subject.configure(data, uiMovieGenres, posterImageConfiguration)
    }
}