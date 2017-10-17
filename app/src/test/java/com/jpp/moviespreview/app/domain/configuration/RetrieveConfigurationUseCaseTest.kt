package com.jpp.moviespreview.app.domain.configuration

import com.jpp.moviespreview.app.data.MoviesConfiguration
import com.jpp.moviespreview.app.data.cache.configuration.MoviesConfigurationCache
import com.jpp.moviespreview.app.data.server.MoviesPreviewApiWrapper
import com.jpp.moviespreview.app.util.TimeUtils
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import java.util.concurrent.TimeUnit

/**
 * Created by jpp on 10/11/17.
 */
class RetrieveConfigurationUseCaseTest {

    private lateinit var subject: RetrieveConfigurationUseCase
    private lateinit var mapper: ConfigurationDataMapper
    private lateinit var api: MoviesPreviewApiWrapper
    private lateinit var mConfigurationCache: MoviesConfigurationCache
    private lateinit var timeUtils: TimeUtils


    @Before
    fun setUp() {
        mapper = mock(ConfigurationDataMapper::class.java)
        api = mock(MoviesPreviewApiWrapper::class.java)
        mConfigurationCache = mock(MoviesConfigurationCache::class.java)
        timeUtils = mock(TimeUtils::class.java)

        subject = RetrieveConfigurationUseCase(mapper, api, mConfigurationCache, timeUtils)
    }


    @Test
    fun execute_whenLastConfigIsOld_retrievesDataFromApi_andSavesNewConfig() {
        `when`(mConfigurationCache.isLastConfigOlderThan(TimeUnit.MINUTES.toMillis(30), timeUtils)).thenReturn(true)
        val moviesConfiguration = mock(MoviesConfiguration::class.java)
        `when`(api.getLastMovieConfiguration()).thenReturn(moviesConfiguration)
        val timestamp = 2000L
        `when`(timeUtils.currentTimeInMillis()).thenReturn(timestamp)

        subject.execute()

        verify(mConfigurationCache).saveMoviesConfig(moviesConfiguration, timestamp)
        verify(mapper).convertMoviesConfigurationFromDataModel(moviesConfiguration)
    }


    @Test
    fun execute_whenLastConfigIsStillValid_retrievesDataFromCache() {
        `when`(mConfigurationCache.isLastConfigOlderThan(TimeUnit.MINUTES.toMillis(30), timeUtils)).thenReturn(false)
        val moviesConfiguration = mock(MoviesConfiguration::class.java)
        `when`(mConfigurationCache.getLastMovieConfiguration()).thenReturn(moviesConfiguration)

        subject.execute()

        verify(mapper).convertMoviesConfigurationFromDataModel(moviesConfiguration)
    }
}


