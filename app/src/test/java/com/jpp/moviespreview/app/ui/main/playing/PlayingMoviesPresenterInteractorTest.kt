package com.jpp.moviespreview.app.ui.main.playing

import com.jpp.moviespreview.app.mock
import com.jpp.moviespreview.app.mockPosterImageConfig
import com.jpp.moviespreview.app.ui.interactors.PresenterInteractorDelegate
import com.jpp.moviespreview.app.ui.sections.main.playing.PlayingMoviesPresenterInteractor
import com.jpp.moviespreview.app.ui.sections.main.playing.PlayingMoviesPresenterInteractorImpl
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by jpp on 12/26/17.
 */
class PlayingMoviesPresenterInteractorTest {

    private lateinit var presenterInteractorDelegate: PresenterInteractorDelegate
    private lateinit var subject: PlayingMoviesPresenterInteractor

    @Before
    fun setUp() {
        presenterInteractorDelegate = mock()
        subject = PlayingMoviesPresenterInteractorImpl(presenterInteractorDelegate)
    }


    @Test
    fun findPosterImageConfigurationForWidth() {
        val mockPosterImageConfig = mockPosterImageConfig()
        val targetWidth = 520
        val expected = mockPosterImageConfig[5]
        val actual = subject.findPosterImageConfigurationForWidth(mockPosterImageConfig, targetWidth)
        assertEquals(expected, actual)
    }

    @Test
    fun findPosterImageConfigurationForWidthAssignsOriginal() {
        val mockPosterImageConfig = mockPosterImageConfig()
        val targetWidth = 1520
        val expected = mockPosterImageConfig[6]
        val actual = subject.findPosterImageConfigurationForWidth(mockPosterImageConfig, targetWidth)
        assertEquals(expected, actual)
    }
}