package com.jpp.moviespreview.app.util.extension

import com.jpp.moviespreview.app.util.extentions.filterInList
import org.junit.Assert
import org.junit.Test

/**
 * Created by jpp on 10/24/17.
 */
class CollectionsExtensionsTests {


    @Test
    fun filterInList() {
        val inputA = listOf(
                StubBeanA(1, "A"),
                StubBeanA(2, "B"),
                StubBeanA(3, "C"),
                StubBeanA(4, "D"),
                StubBeanA(5, "E"),
                StubBeanA(6, "F"))


        val inputB = listOf(
                StubBeanB(1, "A"),
                StubBeanB(2, "B"),
                StubBeanB(4, "D"),
                StubBeanB(6, "F"))

        val func = { x: StubBeanA, y: StubBeanB -> x.value == y.value }
        val result = inputA.filterInList(inputB, func)

        Assert.assertEquals(4, result.size)
        Assert.assertEquals(1, result[0].index)
        Assert.assertEquals(2, result[1].index)
        Assert.assertEquals(4, result[2].index)
        Assert.assertEquals(6, result[3].index)
    }


    private data class StubBeanA(val index: Int, val value: String)
    private data class StubBeanB(val index: Int, val value: String)
}
