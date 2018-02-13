package com.jpp.moviespreview.app.ui.sections.splash

import com.jpp.moviespreview.app.domain.Command
import com.jpp.moviespreview.app.domain.CommandData
import com.jpp.moviespreview.app.mock
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.Error
import com.jpp.moviespreview.app.ui.PosterImageConfiguration
import com.jpp.moviespreview.app.ui.ProfileImageConfiguration
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractor
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.anyOrNull
import com.nhaarman.mockito_kotlin.doAnswer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import com.jpp.moviespreview.app.domain.MoviesConfiguration as DataMoviesConfiguration

/**
 * Created by jpp on 2/13/18.
 */
class SplashPresenterInteractorTest {

    private lateinit var subject: SplashPresenterInteractor
    private lateinit var mapper: DomainToUiDataMapper
    private lateinit var connectivityInteractor: ConnectivityInteractor
    private lateinit var retrieveConfigurationCommand: Command<Any, DataMoviesConfiguration>

    @Before
    fun setup() {
        mapper = mock()
        connectivityInteractor = mock()
        retrieveConfigurationCommand = mock()

        subject = SplashPresenterInteractorImpl(mapper, connectivityInteractor, retrieveConfigurationCommand)
    }


    @Test
    fun retrieveConfigurationNotifiesSuccess() {
        val mockDataMoviesConfiguration: DataMoviesConfiguration = mock()
        val expectedPosterConfig: List<PosterImageConfiguration> = mock()
        val expectedProfileConfig: List<ProfileImageConfiguration> = mock()
        `when`(mapper.convertPosterImageConfigurations(mockDataMoviesConfiguration)).thenReturn(expectedPosterConfig)
        `when`(mapper.convertProfileImageConfigurations(mockDataMoviesConfiguration)).thenReturn(expectedProfileConfig)

        // verify that completes the data and notifies
        val data = SplashData({
            assertTrue(it)
        })

        doAnswer {
            val callback = it.arguments[0] as CommandData<DataMoviesConfiguration>
            callback.value = mockDataMoviesConfiguration
        }.`when`(retrieveConfigurationCommand).execute(any(), anyOrNull())

        subject.retrieveConfiguration(data)

        assertEquals(expectedPosterConfig, data.posterConfig)
        assertEquals(expectedProfileConfig, data.profileConfig)
        assertNull(data.error)
    }

    @Test
    fun retrieveConfigurationNotifiesConnectivityError() {
        `when`(connectivityInteractor.isConnectedToNetwork()).thenReturn(false)

        // verify that completes the error and notifies
        val data = SplashData({
            assertTrue(it)
        })

        doAnswer {
            val callback = it.arguments[0] as CommandData<DataMoviesConfiguration>
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

        // verify that completes the error and notifies
        val data = SplashData({
            assertTrue(it)
        })

        doAnswer {
            val callback = it.arguments[0] as CommandData<DataMoviesConfiguration>
            callback.error = mock() // not important the type
        }.`when`(retrieveConfigurationCommand).execute(any(), anyOrNull())

        subject.retrieveConfiguration(data)
        assertNull(data.posterConfig)
        assertNull(data.profileConfig)
        assertNotNull(data.error)
        assertEquals(data.error!!.type, Error.UNKNOWN)
    }


}