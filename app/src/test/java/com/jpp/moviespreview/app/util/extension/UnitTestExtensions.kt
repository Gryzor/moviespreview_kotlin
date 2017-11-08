package com.jpp.moviespreview.app.util.extension

import com.google.gson.Gson
import com.jpp.moviespreview.app.fromJson

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