package com.jpp.moviespreview.app.domain.configuration

import com.jpp.moviespreview.app.data.MoviesConfiguration
import com.jpp.moviespreview.app.data.cache.MoviesConfigurationCache
import com.jpp.moviespreview.app.data.server.MoviesPreviewApiWrapper
import com.jpp.moviespreview.app.mock
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

/**
 * Created by jpp on 10/11/17.
 */
class RetrieveConfigurationUseCaseTest {

    private lateinit var subject: RetrieveConfigurationUseCase
    private lateinit var mapper: ConfigurationDataMapper
    private lateinit var api: MoviesPreviewApiWrapper
    private lateinit var mConfigurationCache: MoviesConfigurationCache


    @Before
    fun setUp() {
        mapper = mock()
        api = mock()
        mConfigurationCache = mock()

        subject = RetrieveConfigurationUseCase(mapper, api, mConfigurationCache)
    }


    @Test
    fun execute_whenLastConfigIsOld_retrievesDataFromApi_andSavesNewConfig() {
        `when`(mConfigurationCache.isMoviesConfigurationOutOfDate()).thenReturn(true)
        val moviesConfiguration: MoviesConfiguration = mock()
        `when`(api.getLastMovieConfiguration()).thenReturn(moviesConfiguration)
        val timestamp = 2000L

        subject.execute()

        verify(mConfigurationCache).saveMoviesConfig(moviesConfiguration)
        verify(mapper).convertMoviesConfigurationFromDataModel(moviesConfiguration)
    }


    @Test
    fun execute_whenLastConfigIsOld_andDataRetrievedFromApiIsNull_returnsNull() {
        `when`(mConfigurationCache.isMoviesConfigurationOutOfDate()).thenReturn(true)
        `when`(api.getLastMovieConfiguration()).thenReturn(null)

        val result = subject.execute()

        Assert.assertNull(result)
    }


    @Test
    fun execute_whenLastConfigIsStillValid_retrievesDataFromCache() {
        `when`(mConfigurationCache.isMoviesConfigurationOutOfDate()).thenReturn(false)
        val moviesConfiguration: MoviesConfiguration = mock()
        `when`(mConfigurationCache.getLastMovieConfiguration()).thenReturn(moviesConfiguration)

        subject.execute()

        verify(mapper).convertMoviesConfigurationFromDataModel(moviesConfiguration)
    }

    @Test
    fun execute_whenLastConfigIsStillValid_andDataRetievedFromCacheIsNull() {
        `when`(mConfigurationCache.isMoviesConfigurationOutOfDate()).thenReturn(false)
        `when`(mConfigurationCache.getLastMovieConfiguration()).thenReturn(null)

        val result = subject.execute()

        Assert.assertNull(result)
    }
}


