package com.jpp.moviespreview.app.ui.sections.detail.credits

import com.jpp.moviespreview.app.BackgroundExecutorForTesting
import com.jpp.moviespreview.app.mock
import com.jpp.moviespreview.app.ui.*
import com.jpp.moviespreview.app.ui.interactors.BackgroundExecutor
import com.jpp.moviespreview.app.ui.interactors.ImageConfigurationManager
import com.jpp.moviespreview.app.ui.sections.detail.CreditsData
import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailCreditsInteractor
import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailCreditsPresenter
import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailCreditsView
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`

/**
 * Created by jpp on 3/13/18.
 */
class MovieDetailCreditsPresenterTest {

    private lateinit var moviesContextHandler: MoviesContextHandler
    private lateinit var backgroundExecutor: BackgroundExecutor
    private lateinit var interactor: MovieDetailCreditsInteractor
    private lateinit var imageConfigManager: ImageConfigurationManager
    private lateinit var viewInstance: MovieDetailCreditsView
    private lateinit var subject: MovieDetailCreditsPresenter


    @Before
    fun setUp() {
        moviesContextHandler = mock()
        interactor = mock()
        imageConfigManager = mock()
        viewInstance = mock()

        backgroundExecutor = BackgroundExecutorForTesting()

        subject = MovieDetailCreditsPresenterImpl(moviesContextHandler, backgroundExecutor, interactor, imageConfigManager)
    }

    @Test
    fun linkViewWhenCreditsAreInContextDoesNotExecutesCommand() {
        val selectedMovie: Movie = mock()
        val expectedCredits: List<CreditPerson> = mock()

        `when`(moviesContextHandler.getSelectedMovie()).thenReturn(selectedMovie)
        `when`(moviesContextHandler.getCreditsForMovie(selectedMovie)).thenReturn(expectedCredits)

        subject.linkView(viewInstance)

        verify(viewInstance).showMovieCredits(expectedCredits)
        verifyZeroInteractions(interactor)
    }


    @Test
    fun linkViewWhenCreditsAreNotInContextShowsLoadingAndRetrieveCreditsWithCommand() {
        configCommandExecution()

        subject.linkView(viewInstance)

        verify(viewInstance).showLoading()
        verify(interactor).retrieveMovieCredits(any(), any(), any())
    }


    @Test
    fun linkViewWhenCreditsAreNotInContextAndCommandRetrievesCreditsOK() {
        configCommandExecution()
        val expectedCredits: List<CreditPerson> = mock()

        doAnswer {
            val creditsData = it.arguments[0] as CreditsData
            creditsData.credits = expectedCredits
        }.`when`(interactor).retrieveMovieCredits(any(), any(), any())

        subject.linkView(viewInstance)

        verify(viewInstance).showMovieCredits(expectedCredits)
    }


    @Test
    fun linkViewWhenCreditsAreNotInContextAndCommandRetrievesFails() {
        configCommandExecution()

        doAnswer {
            val creditsData = it.arguments[0] as CreditsData
            creditsData.error = Error(Error.UNKNOWN)
        }.`when`(interactor).retrieveMovieCredits(any(), any(), any())

        subject.linkView(viewInstance)

        verify(viewInstance).showErrorRetrievingCredits()
    }


    private fun configCommandExecution() {
        val selectedMovie: Movie = mock()
        val profileImageConfigs: List<ProfileImageConfiguration> = mock()
        val profileImageHeight = 20
        val selectedImageConfig: ProfileImageConfiguration = mock()

        `when`(moviesContextHandler.getSelectedMovie()).thenReturn(selectedMovie)
        `when`(moviesContextHandler.getCreditsForMovie(selectedMovie)).thenReturn(null)
        `when`(moviesContextHandler.getProfileImageConfigs()).thenReturn(profileImageConfigs)
        `when`(viewInstance.getTargetProfileImageHeight()).thenReturn(profileImageHeight)
        `when`(imageConfigManager.findProfileImageConfigurationForHeight(profileImageConfigs, profileImageHeight)).thenReturn(selectedImageConfig)
    }

}