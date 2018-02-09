package com.jpp.moviespreview.app.ui.interactors

import com.jpp.moviespreview.app.mockPosterImageConfig
import com.jpp.moviespreview.app.ui.ProfileImageConfiguration
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by jpp on 1/15/18.
 */
class ImageConfigurationInteractorTest {

    private val subject = ImageConfigurationInteractorImpl()


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