package com.jpp.moviespreview.app.util.extension

import com.google.gson.Gson
import com.jpp.moviespreview.app.util.extentions.fromJson
import org.junit.Assert
import org.junit.Assert.assertEquals

/**
 * Created by jpp on 11/8/17.
 */
inline fun <reified T> loadObjectFromJsonFile(classLoader: ClassLoader, fileName: String): T {
    val input = classLoader.getResourceAsStream(fileName)
    val size = input.available()
    val buffer = ByteArray(size)
    input.read(buffer)
    input.close()
    return Gson().fromJson(String(buffer))
}


fun fuzzyAssert(expected: Double, actual: Double) {
    return assertEquals(expected, actual, 0.001)
}