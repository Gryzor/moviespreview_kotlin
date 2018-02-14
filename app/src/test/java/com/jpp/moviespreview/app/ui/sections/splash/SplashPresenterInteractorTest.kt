package com.jpp.moviespreview.app.ui.sections.splash

import com.jpp.moviespreview.app.domain.Command
import com.jpp.moviespreview.app.domain.CommandData
import com.jpp.moviespreview.app.domain.Genre
import com.jpp.moviespreview.app.mock
import com.jpp.moviespreview.app.ui.*
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractor
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.anyOrNull
import com.nhaarman.mockito_kotlin.doAnswer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import com.jpp.moviespreview.app.domain.Genre as DomainGenre
import com.jpp.moviespreview.app.domain.MoviesConfiguration as DomainMoviesConfiguration

/**
 * Created by jpp on 2/13/18.
 */
class SplashPresenterInteractorTest {

    private lateinit var subject: SplashPresenterInteractor
    private lateinit var mapper: DomainToUiDataMapper
    private lateinit var connectivityInteractor: ConnectivityInteractor
    private lateinit var retrieveConfigurationCommand: Command<Any, DomainMoviesConfiguration>
    private lateinit var retrieveGenresCommand: Command<Any, List<Genre>>

    @Before
    fun setup() {
        mapper = mock()
        connectivityInteractor = mock()
        retrieveConfigurationCommand = mock()
        retrieveGenresCommand = mock()

        subject = SplashPresenterInteractorImpl(mapper, connectivityInteractor, retrieveConfigurationCommand, retrieveGenresCommand)
    }


    @Test
    fun retrieveConfigurationNotifiesSuccess() {
        val mockDomainMoviesConfiguration: DomainMoviesConfiguration = mock()
        val mockDomainGenres: List<DomainGenre> = mock()
        val expectedPosterConfig: List<PosterImageConfiguration> = mock()
        val expectedProfileConfig: List<ProfileImageConfiguration> = mock()
        val expectedMovieGenres: List<MovieGenre> = mock()

        `when`(mapper.convertPosterImageConfigurations(mockDomainMoviesConfiguration)).thenReturn(expectedPosterConfig)
        `when`(mapper.convertProfileImageConfigurations(mockDomainMoviesConfiguration)).thenReturn(expectedProfileConfig)
        `when`(mapper.convertDomainGenresIntoUiGenres(mockDomainGenres)).thenReturn(expectedMovieGenres)

        val data = SplashData()

        doAnswer {
            val callback = it.arguments[0] as CommandData<DomainMoviesConfiguration>
            callback.value = mockDomainMoviesConfiguration
        }.`when`(retrieveConfigurationCommand).execute(any(), anyOrNull())

        doAnswer {
            val callback = it.arguments[0] as CommandData<List<DomainGenre>>
            callback.value = mockDomainGenres
        }.`when`(retrieveGenresCommand).execute(any(), anyOrNull())

        subject.retrieveConfiguration(data)

        assertEquals(expectedPosterConfig, data.posterConfig)
        assertEquals(expectedProfileConfig, data.profileConfig)
        assertEquals(expectedMovieGenres, data.movieGenres)
        assertNull(data.error)
    }

    @Test
    fun retrieveConfigurationNotifiesConnectivityError() {
        `when`(connectivityInteractor.isConnectedToNetwork()).thenReturn(false)

        val data = SplashData()

        doAnswer {
            val callback = it.arguments[0] as CommandData<DomainMoviesConfiguration>
            callback.error = mock() // not important the type
        }.`when`(retrieveConfigurationCommand).execute(any(), anyOrNull())

        subject.retrieveConfiguration(data)
        assertNull(data.posterConfig)
        assertNull(data.profileConfig)
        assertNotNull(data.error)
        assertEquals(data.error!!.type, Error.NO_CONNECTION)
    }


    @Test
    fun retrieveConfigurationNotifiesUnknownError() {
        `when`(connectivityInteractor.isConnectedToNetwork()).thenReturn(true)

        val data = SplashData()

        doAnswer {
            val callback = it.arguments[0] as CommandData<DomainMoviesConfiguration>
            callback.error = mock() // not important the type
        }.`when`(retrieveConfigurationCommand).execute(any(), anyOrNull())

        subject.retrieveConfiguration(data)
        assertNull(data.posterConfig)
        assertNull(data.profileConfig)
        assertNotNull(data.error)
        assertEquals(data.error!!.type, Error.UNKNOWN)
    }


}