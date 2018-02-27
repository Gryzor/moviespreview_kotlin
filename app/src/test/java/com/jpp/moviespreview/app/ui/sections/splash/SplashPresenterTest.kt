package com.jpp.moviespreview.app.ui.sections.splash

import com.jpp.moviespreview.app.BackgroundExecutorForTesting
import com.jpp.moviespreview.app.mock
import com.jpp.moviespreview.app.ui.*
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.spy

/**
 * Created by jpp on 2/16/18.
 */
class SplashPresenterTest {

    private lateinit var moviesContext: ApplicationMoviesContext
    private lateinit var backgroundExcutor: BackgroundExecutorForTesting
    private lateinit var interactor: SplashPresenterInteractor
    private lateinit var splashView: SplashView
    private lateinit var subject: SplashPresenter


    @Before
    fun setUp() {
        backgroundExcutor = BackgroundExecutorForTesting()

        moviesContext = spy(ApplicationMoviesContext::class.java)
        interactor = mock()
        splashView = mock()

        subject = SplashPresenterImpl(moviesContext, backgroundExcutor, interactor)
    }


    @Test
    fun linkView_whenContextCompleted_continuesToHome() {
        `when`(moviesContext.isConfigCompleted()).thenReturn(true)

        subject.linkView(splashView)

        verify(splashView).continueToHome()
    }

    @Test
    fun linkView_whenContextNotCompleted_retrievesConfigInBAckground() {
        `when`(moviesContext.isConfigCompleted()).thenReturn(false)

        subject.linkView(splashView)

        verify(interactor).retrieveConfiguration(any())
        assertTrue(backgroundExcutor.backgroundExecuted)
    }


    @Test
    fun linkView_whenContextNotCompleted_completesContext_andContinuesToHome() {
        val expectedPosterImageConfig: List<PosterImageConfiguration> = listOf(mock())
        val expectedProfileImageConfig: List<ProfileImageConfiguration> = listOf(mock())
        val expectedMovieGenres: List<MovieGenre> = listOf(mock())

        doAnswer {

            val splashData = it.arguments[0] as SplashData
            splashData.posterConfig = expectedPosterImageConfig
            splashData.profileConfig = expectedProfileImageConfig
            splashData.movieGenres = expectedMovieGenres

        }.`when`(interactor).retrieveConfiguration(any())

        subject.linkView(splashView)

        verify(moviesContext).posterImageConfig = expectedPosterImageConfig
        verify(moviesContext).profileImageConfig = expectedProfileImageConfig
        verify(moviesContext).movieGenres = expectedMovieGenres
        verify(splashView).continueToHome()
    }


    @Test
    fun linkView_whenContextNotCompleted_requestFails_showsMessage() {
        doAnswer {

            val splashData = it.arguments[0] as SplashData
            splashData.error = Error(Error.UNKNOWN)

        }.`when`(interactor).retrieveConfiguration(any())

        subject.linkView(splashView)

        verify(splashView).showUnexpectedError()
    }

    @Test
    fun linkView_whenContextNotCompleted_requestFails_showsNoNetworkMessage() {
        doAnswer {

            val splashData = it.arguments[0] as SplashData
            splashData.error = Error(Error.NO_CONNECTION)

        }.`when`(interactor).retrieveConfiguration(any())

        subject.linkView(splashView)

        verify(splashView).showNotConnectedToNetwork()
    }


    @Test
    fun linkView_whenContextNotCompleted_genresRequestFails_showsMessage() {
        val expectedPosterImageConfig: List<PosterImageConfiguration> = listOf(mock())
        val expectedProfileImageConfig: List<ProfileImageConfiguration> = listOf(mock())

        doAnswer {

            val splashData = it.arguments[0] as SplashData
            splashData.posterConfig = expectedPosterImageConfig
            splashData.profileConfig = expectedProfileImageConfig
            splashData.error = Error(Error.UNKNOWN)

        }.`when`(interactor).retrieveConfiguration(any())

        subject.linkView(splashView)

        verify(moviesContext).posterImageConfig = expectedPosterImageConfig
        verify(moviesContext).profileImageConfig = expectedProfileImageConfig
        verify(splashView).showUnexpectedError()
    }

}