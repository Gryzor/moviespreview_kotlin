package com.jpp.moviespreview.app.ui.splash


import com.jpp.moviespreview.app.BackgroundInteractorForTesting
import com.jpp.moviespreview.app.domain.Genre
import com.jpp.moviespreview.app.domain.ImagesConfiguration
import com.jpp.moviespreview.app.domain.MoviesConfiguration
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.mock
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.interactors.BackgroundInteractor
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractor
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito


/**
 * Tests Presenter + DomainToUiDataMapper
 *
 * Created by jpp on 10/11/17.
 */
class SplashPresenterImplTest {

    private lateinit var moviesConfigurationUseCase: UseCase<Any, MoviesConfiguration>
    private lateinit var moviesGenresUseCase: UseCase<Any, List<Genre>>
    private lateinit var backgroundInteractor: BackgroundInteractor
    private lateinit var moviesContext: MoviesContext
    private lateinit var mapper: DomainToUiDataMapper
    private lateinit var splashView: SplashView
    private lateinit var subject: SplashPresenterImpl
    private lateinit var connectivityInteractor: ConnectivityInteractor


    @Before
    fun doBefore() {
        backgroundInteractor = BackgroundInteractorForTesting()
        moviesContext = Mockito.spy(MoviesContext::class.java)
        mapper = DomainToUiDataMapper()
        moviesConfigurationUseCase = mock()
        moviesGenresUseCase = mock()
        splashView = mock()
        connectivityInteractor = mock()

        subject = SplashPresenterImpl(moviesContext,backgroundInteractor, mapper, connectivityInteractor, moviesConfigurationUseCase, moviesGenresUseCase)
    }

    @Test
    fun linkView_whenContextCompleted_doesNotExecutesUseCase_andContinuesToHome() {
        //-- prepare
        Mockito.`when`(moviesContext.isConfigCompleted()).thenReturn(true)

        // -- execute
        subject.linkView(splashView)

        // -- verify
        Mockito.verify(splashView).continueToHome()
        Mockito.verifyZeroInteractions(moviesConfigurationUseCase, moviesGenresUseCase)
    }

    @Test
    fun linkView_whenContextNotCompleted_retrievesConfigAndGenres() {
        // -- execute
        subject.linkView(splashView)

        // -- verify
        Mockito.verify(moviesConfigurationUseCase).execute()
        Mockito.verify(moviesGenresUseCase).execute()
    }

    @Test
    fun linkView_whenContextNotCompleted_retrievesConfigAndGenres_setsContext_andContinuesToHome() {
        // -- prepare
        val movieConfigRetrieved: MoviesConfiguration = mock()
        val rawSizes = ArrayList<String>()
        rawSizes.add("1")
        val sizes = ImagesConfiguration("randomUrl", rawSizes)
        Mockito.`when`(movieConfigRetrieved.posterImagesConfiguration).thenReturn(sizes)
        Mockito.`when`(moviesConfigurationUseCase.execute()).thenReturn(movieConfigRetrieved)
        val genreList = ArrayList<Genre>()
        genreList.add(Genre(12, "Random"))
        Mockito.`when`(moviesGenresUseCase.execute()).thenReturn(genreList)

        // -- execute
        subject.linkView(splashView)

        // -- verify
        Assert.assertNotNull(moviesContext.imageConfig)
        Assert.assertNotNull(moviesContext.movieGenres)
        Mockito.verify(splashView).continueToHome()
    }

    @Test
    fun linkView_whenContextNotCompleted_andConfigUseCaseFails_showsError() {
        // -- prepare
        Mockito.`when`(moviesConfigurationUseCase.execute()).thenReturn(null)
        val genreList = ArrayList<Genre>()
        Mockito.`when`(moviesGenresUseCase.execute()).thenReturn(genreList)
        Mockito.`when`(connectivityInteractor.isConnectedToNetwork()).thenReturn(true)

        // -- execute
        subject.linkView(splashView)

        // -- verify
        Assert.assertNotNull(moviesContext.movieGenres)
        Mockito.verify(splashView).showUnexpectedError()
        Mockito.verifyNoMoreInteractions(splashView)
    }

    @Test
    fun linkView_whenContextNotCompleted_andGenresUseCaseFails_showsError() {
        // -- prepare
        val movieConfigRetrieved: MoviesConfiguration = mock()
        val sizes = ImagesConfiguration("randomUrl", ArrayList())
        Mockito.`when`(movieConfigRetrieved.posterImagesConfiguration).thenReturn(sizes)
        Mockito.`when`(moviesConfigurationUseCase.execute()).thenReturn(movieConfigRetrieved)
        Mockito.`when`(moviesGenresUseCase.execute()).thenReturn(null)
        Mockito.`when`(connectivityInteractor.isConnectedToNetwork()).thenReturn(true)

        // -- execute
        subject.linkView(splashView)

        // -- verify
        Assert.assertNotNull(moviesContext.imageConfig)
        Mockito.verify(splashView).showUnexpectedError()
        Mockito.verifyNoMoreInteractions(splashView)
    }


    @Test
    fun linkView_whenContextNotCompleted_andConfigUseCaseFails_noConnection_showsError() {
        // -- prepare
        Mockito.`when`(moviesConfigurationUseCase.execute()).thenReturn(null)
        val genreList = ArrayList<Genre>()
        Mockito.`when`(moviesGenresUseCase.execute()).thenReturn(genreList)
        Mockito.`when`(connectivityInteractor.isConnectedToNetwork()).thenReturn(false)

        // -- execute
        subject.linkView(splashView)

        // -- verify
        Assert.assertNotNull(moviesContext.movieGenres)
        Mockito.verify(splashView).showNotConnectedToNetwork()
        Mockito.verifyNoMoreInteractions(splashView)
    }

    @Test
    fun linkView_whenContextNotCompleted_andGenresUseCaseFails_noConnection_showsError() {
        // -- prepare
        val movieConfigRetrieved: MoviesConfiguration = mock()
        val sizes = ImagesConfiguration("randomUrl", ArrayList())
        Mockito.`when`(movieConfigRetrieved.posterImagesConfiguration).thenReturn(sizes)
        Mockito.`when`(moviesConfigurationUseCase.execute()).thenReturn(movieConfigRetrieved)
        Mockito.`when`(moviesGenresUseCase.execute()).thenReturn(null)
        Mockito.`when`(connectivityInteractor.isConnectedToNetwork()).thenReturn(false)

        // -- execute
        subject.linkView(splashView)

        // -- verify
        Assert.assertNotNull(moviesContext.imageConfig)
        Mockito.verify(splashView).showNotConnectedToNetwork()
        Mockito.verifyNoMoreInteractions(splashView)
    }

    @Test
    fun linkView_whenContextNotCompleted_andBothUseCasesFail_noConnection_showsError() {
        // -- prepare
        Mockito.`when`(moviesConfigurationUseCase.execute()).thenReturn(null)
        Mockito.`when`(moviesGenresUseCase.execute()).thenReturn(null)
        Mockito.`when`(connectivityInteractor.isConnectedToNetwork()).thenReturn(false)

        // -- execute
        subject.linkView(splashView)

        // -- verify
        Mockito.verify(splashView, Mockito.times(2)).showNotConnectedToNetwork()
    }

    @Test
    fun linkView_whenContextNotCompleted_andBothUseCasesFail_connected_showsError() {
        // -- prepare
        Mockito.`when`(moviesConfigurationUseCase.execute()).thenReturn(null)
        Mockito.`when`(moviesGenresUseCase.execute()).thenReturn(null)
        Mockito.`when`(connectivityInteractor.isConnectedToNetwork()).thenReturn(true)

        // -- execute
        subject.linkView(splashView)

        // -- verify
        Mockito.verify(splashView, Mockito.times(2)).showUnexpectedError()
    }


    @Test
    fun linkView_whenContextNotCompleted_andBothUseCasesThrowException_showsError() {
        // -- prepare
        (backgroundInteractor as BackgroundInteractorForTesting).throwException = true
        Mockito.`when`(moviesConfigurationUseCase.execute()).thenReturn(null)
        Mockito.`when`(moviesGenresUseCase.execute()).thenReturn(null)
        Mockito.`when`(connectivityInteractor.isConnectedToNetwork()).thenReturn(true)

        // -- execute
        subject.linkView(splashView)

        // -- verify
        Mockito.verify(splashView, Mockito.times(2)).showUnexpectedError()
    }

}