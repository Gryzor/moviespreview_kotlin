package com.jpp.moviespreview.app.ui

import com.jpp.moviespreview.app.ui.util.mockImageConfig
import org.junit.Assert
import org.junit.Test

/**
 * Created by jpp on 10/27/17.
 */
class MoviesContextTest {

    @Test
    fun getImageConfigForScreenWidth_selectsProperImageConfigWhenPossible() {
        val subject = MoviesContext()
        val list = subject.mockImageConfig()
        subject.imageConfig = list
        val expected = list[5]
        val actual = subject.getImageConfigForScreenWidth(700)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun getImageConfigForScreenWidth_selectsProperImageConfigWhenNotPossible_selectsDefault() {
        val subject = MoviesContext()
        val list = subject.mockImageConfig()
        subject.imageConfig = list
        val expected = list[6]
        val actual = subject.getImageConfigForScreenWidth(1260)
        Assert.assertEquals(expected, actual)
    }
}