package com.jpp.moviespreview.app.ui.detail.credits

import com.jpp.moviespreview.app.mock
import com.jpp.moviespreview.app.ui.ProfileImageConfiguration
import com.jpp.moviespreview.app.ui.interactors.PresenterInteractorDelegate
import com.jpp.moviespreview.app.ui.sections.detail.credits.MovieDetailsCreditsPresenterInteractor
import com.jpp.moviespreview.app.ui.sections.detail.credits.MovieDetailsCreditsPresenterInteractorImpl
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by jpp on 1/2/18.
 */

class MovieDetailsCreditsPresenterInteractorTest {

    private lateinit var presenterInteractorDelegate: PresenterInteractorDelegate
    private lateinit var subject: MovieDetailsCreditsPresenterInteractor


    @Before
    fun setUp() {
        presenterInteractorDelegate = mock()
        subject = MovieDetailsCreditsPresenterInteractorImpl(presenterInteractorDelegate)
    }


    @Test
    fun findProfileImageConfigurationForHeight() {
        val profileImageConfigs = listOf(
                ProfileImageConfiguration("someUrl", "h100"),
                ProfileImageConfiguration("someUrl", "h200"),
                ProfileImageConfiguration("someUrl", "h300"),
                ProfileImageConfiguration("someUrl", "h400"),
                ProfileImageConfiguration("someUrl", "h500"),
                ProfileImageConfiguration("someUrl", "original")
        )
        val height = 350
        val expected = profileImageConfigs[3]
        val actual = subject.findProfileImageConfigurationForHeight(profileImageConfigs, height)
        assertEquals(actual, expected)
    }


    @Test
    fun findProfileImageConfigurationForHeightExact() {
        val profileImageConfigs = listOf(
                ProfileImageConfiguration("someUrl", "h100"),
                ProfileImageConfiguration("someUrl", "h200"),
                ProfileImageConfiguration("someUrl", "h300"),
                ProfileImageConfiguration("someUrl", "h400"),
                ProfileImageConfiguration("someUrl", "h500"),
                ProfileImageConfiguration("someUrl", "original")
        )
        val height = 300
        val expected = profileImageConfigs[3]
        val actual = subject.findProfileImageConfigurationForHeight(profileImageConfigs, height)
        assertEquals(actual, expected)
    }


    @Test
    fun findProfileImageConfigurationForHeightReturnLast() {
        val profileImageConfigs = listOf(
                ProfileImageConfiguration("someUrl", "h100"),
                ProfileImageConfiguration("someUrl", "h200"),
                ProfileImageConfiguration("someUrl", "h300"),
                ProfileImageConfiguration("someUrl", "h400"),
                ProfileImageConfiguration("someUrl", "h500"),
                ProfileImageConfiguration("someUrl", "original")
        )
        val height = 850
        val expected = profileImageConfigs[5]
        val actual = subject.findProfileImageConfigurationForHeight(profileImageConfigs, height)
        assertEquals(actual, expected)
    }

}