package com.jpp.moviespreview.app.domain.configuration

import com.jpp.moviespreview.app.data.cache.MoviesConfigurationCache
import com.jpp.moviespreview.app.data.server.MoviesPreviewApiWrapper
import com.jpp.moviespreview.app.domain.CommandData
import com.jpp.moviespreview.app.domain.MoviesConfiguration
import com.jpp.moviespreview.app.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import com.jpp.moviespreview.app.data.MoviesConfiguration as DataMoviesConfiguration

/**
 * Created by jpp on 2/12/18.
 */
class RetrieveConfigurationCommandTest {

    private lateinit var subject: RetrieveConfigurationCommand
    private lateinit var mapper: ConfigurationDataMapper
    private lateinit var api: MoviesPreviewApiWrapper
    private lateinit var configurationCache: MoviesConfigurationCache


    @Before
    fun setUp() {
        mapper = mock()
        api = mock()
        configurationCache = mock()
        subject = RetrieveConfigurationCommand(mapper, api, configurationCache)
    }

    @Test
    fun execute_whenLastConfigIsOld_retrievesDataFromApi_andSavesNewConfig() {
        val expected: MoviesConfiguration = mock()
        val dataMoviesConfiguration: DataMoviesConfiguration = mock()

        `when`(configurationCache.isMoviesConfigurationOutOfDate()).thenReturn(true)
        `when`(api.getLastMovieConfiguration()).thenReturn(dataMoviesConfiguration)
        `when`(configurationCache.saveMoviesConfig(dataMoviesConfiguration)).thenReturn(dataMoviesConfiguration)
        `when`(mapper.convertMoviesConfigurationFromDataModel(dataMoviesConfiguration)).thenReturn(expected)

        val data = CommandData(
                {
                    actual: MoviesConfiguration ->
                    assertEquals(expected, actual)
                },
                {
                    fail()
                }
        )

        subject.execute(data)

        verify(configurationCache).saveMoviesConfig(dataMoviesConfiguration)
        assertEquals(expected, data.value)
    }


    @Test
    fun execute_whenLastConfigIsOld_andDataRetrievedFromApiIsNull_returnsNull() {
        `when`(configurationCache.isMoviesConfigurationOutOfDate()).thenReturn(true)
        `when`(api.getLastMovieConfiguration()).thenReturn(null)

        val data = CommandData(
                {
                    actual: MoviesConfiguration ->
                    fail()
                },
                {
                    assertTrue(it is IllegalStateException)
                }
        )

        subject.execute(data)

        assertNotNull(data.error)
    }


    @Test
    fun execute_whenLastConfigIsStillValid_retrievesDataFromCache() {
        val expected: MoviesConfiguration = mock()
        val dataMoviesConfiguration: DataMoviesConfiguration = mock()

        `when`(configurationCache.isMoviesConfigurationOutOfDate()).thenReturn(false)
        `when`(configurationCache.getLastMovieConfiguration()).thenReturn(dataMoviesConfiguration)
        `when`(mapper.convertMoviesConfigurationFromDataModel(dataMoviesConfiguration)).thenReturn(expected)

        val data = CommandData(
                {
                    actual: MoviesConfiguration ->
                    assertEquals(expected, actual)
                },
                {
                    fail()
                }
        )

        subject.execute(data)

        assertEquals(expected, data.value)
    }

    @Test
    fun execute_whenLastConfigIsStillValid_andDataRetievedFromCacheIsNull() {
        `when`(configurationCache.isMoviesConfigurationOutOfDate()).thenReturn(false)
        `when`(configurationCache.getLastMovieConfiguration()).thenReturn(null)

        val data = CommandData(
                {
                    actual: MoviesConfiguration ->
                    fail()
                },
                {
                    assertTrue(it is IllegalStateException)
                }
        )

        subject.execute(data)

        assertNotNull(data.error)
    }
}