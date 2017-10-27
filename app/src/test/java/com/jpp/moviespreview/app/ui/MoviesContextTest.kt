package com.jpp.moviespreview.app.ui

import org.junit.Assert
import org.junit.Test

/**
 * Created by jpp on 10/27/17.
 */
class MoviesContextTest {

    @Test
    fun getImageConfigForScreenWidth_selectsProperImageConfigWhenPossible() {
        val subject = MoviesContext()
        val list = createImageConfigList()
        subject.imageConfig = list
        val expected = list[5]
        val actual = subject.getImageConfigForScreenWidth(700)
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun getImageConfigForScreenWidth_selectsProperImageConfigWhenNotPossible_selectsDefault() {
        val subject = MoviesContext()
        val list = createImageConfigList()
        subject.imageConfig = list
        val expected = list[6]
        val actual = subject.getImageConfigForScreenWidth(1260)
        Assert.assertEquals(expected, actual)
    }


    private fun createImageConfigList() = listOf(
            ImageConfiguration("url", "w92", 92),
            ImageConfiguration("url", "w154", 154),
            ImageConfiguration("url", "w185", 185),
            ImageConfiguration("url", "w342", 342),
            ImageConfiguration("url", "w500", 500),
            ImageConfiguration("url", "w780", 780),
            ImageConfiguration("url", "original", -1))

}