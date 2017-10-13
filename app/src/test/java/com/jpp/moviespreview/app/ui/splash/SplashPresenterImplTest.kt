package com.jpp.moviespreview.app.ui.splash


import com.jpp.moviespreview.app.domain.MoviesConfiguration
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.mock

import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.ImageConfiguration
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.background.BackgroundInteractor
import com.jpp.moviespreview.app.BackgroundInteractorForTesting
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito


/**
 * Created by jpp on 10/11/17.
 */
class SplashPresenterImplTest {

    private lateinit var useCase: UseCase<Any, MoviesConfiguration>
    private lateinit var backgroundInteractor: BackgroundInteractor
    private lateinit var moviesContext: MoviesContext
    private lateinit var mapper: DomainToUiDataMapper
    private lateinit var splashView: SplashView
    private lateinit var subject: SplashPresenterImpl


    @Before
    fun doBefore() {
        backgroundInteractor = BackgroundInteractorForTesting()
        moviesContext = MoviesContext()
        mapper = mock()
        useCase = mock()
        splashView = mock()

        subject = SplashPresenterImpl(useCase, backgroundInteractor, moviesContext, mapper)
        subject.linkView(splashView)
    }


    @Test
    fun retrieveConfig_whenNoImageConfig_executesUseCase() {
        subject.retrieveConfig()

        Mockito.verify(useCase).execute()
    }

    @Test
    fun retrieveConfig_whenNoImageConfig_andUseCaseRetrievesConfig_setsValueInContext_andContinues() {
        val expectedImageConfig: ImageConfiguration = mock()
        val movieConfigRetrieved: MoviesConfiguration = mock()
        Mockito.`when`(useCase.execute()).thenReturn(movieConfigRetrieved)
        Mockito.`when`(mapper.convertConfigurationToImagesConfiguration(movieConfigRetrieved)).thenReturn(expectedImageConfig)

        subject.retrieveConfig()

        Mockito.verify(mapper).convertConfigurationToImagesConfiguration(movieConfigRetrieved)
        Assert.assertNotNull(moviesContext.imageConfig)
        Assert.assertEquals(expectedImageConfig, moviesContext.imageConfig)
        Mockito.verify(splashView).continueToHome()
    }


    @Test
    fun retrieveConfig_whenNoImageConfig_andUseCaseReturnsNull_callsOnShowError() {
        Mockito.`when`(useCase.execute()).thenReturn(null)

        subject.retrieveConfig()

        Mockito.verify(splashView).showError()
    }

    @Test
    fun retrieveConfig_whenNoImageConfig_andUseCaseThrowsException_callsOnShowError() {
        Mockito.`when`(useCase.execute()).thenReturn(null)
        (backgroundInteractor as BackgroundInteractorForTesting).throwException = true

        subject.retrieveConfig()

        Mockito.verify(splashView).showError()
    }

    @Test
    fun retrieveConfig_whenThereIsImageConfig_doesNotExecutesUseCase_continuesToHome() {
        val expectedImageConfig: ImageConfiguration = mock()
        moviesContext.imageConfig = expectedImageConfig

        subject.retrieveConfig()

        Mockito.verify(splashView).continueToHome()
        Mockito.verifyZeroInteractions(useCase, mapper)
    }

}