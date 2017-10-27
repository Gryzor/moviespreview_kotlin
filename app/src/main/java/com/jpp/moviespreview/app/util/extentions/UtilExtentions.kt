package com.jpp.moviespreview.app.util.extentions

import java.util.*

/**
 * Transforms a String into it's integer representation by removing all
 * existing letters.
 * If the string does not contains Ints, the [defaultValue] is returned.
 */
fun String.transformToInt(defaultValue: Int = -1): Int {
    if (!this.matches(".*\\d.*".toRegex())) {
        // string does not has numbers
        return defaultValue
    }

    // extract integer part
    val scanner = Scanner(this).useDelimiter("[^0-9]+")
    return scanner.nextInt()
}