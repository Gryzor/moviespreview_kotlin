package com.jpp.moviespreview.app.util.extension

import com.jpp.moviespreview.app.util.extentions.transformToInt
import org.junit.Assert
import org.junit.Test

/**
 * Created by jpp on 10/27/17.
 */
class UtilExtentsionsTests {

    @Test
    fun transformToInt() {
        var expected = 92
        var actual = "w92".transformToInt()
        Assert.assertEquals(expected, actual)

        expected = 154
        actual = "w154".transformToInt()
        Assert.assertEquals(expected, actual)

        expected = 185
        actual = "w185".transformToInt()
        Assert.assertEquals(expected, actual)

        expected = 342
        actual = "w342".transformToInt()
        Assert.assertEquals(expected, actual)

        expected = 500
        actual = "w500".transformToInt()
        Assert.assertEquals(expected, actual)

        expected = 780
        actual = "w780".transformToInt()
        Assert.assertEquals(expected, actual)

        actual = "original".transformToInt()
        Assert.assertNull(actual)
    }
}