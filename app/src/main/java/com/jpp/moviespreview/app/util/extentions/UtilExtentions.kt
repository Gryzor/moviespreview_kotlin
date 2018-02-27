package com.jpp.moviespreview.app.util.extentions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 * Transforms a String into it's integer representation by removing all
 * existing letters.
 * If the string does not contains Ints, null is returned.
 */
fun String.transformToInt(): Int? {
    if (!this.matches(".*\\d.*".toRegex())) {
        // string does not has numbers
        return null
    }

    // extract integer part
    val scanner = Scanner(this).useDelimiter("[^0-9]+")
    return scanner.nextInt()
}


/**
 * Helper class to load an object from GSON
 */
inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)!!

/**
 * Extension function to execute a [callback] when the provided [input] is not null.
 */
inline fun <T : Any, R> whenNotNull(input: T?, callback: (T) -> R): R? = input?.let(callback)

/**
 * Extension function to execute a [callback] when the provided [input] is null.
 */
inline fun <T : Any, R> whenNull(input: T?, callback: () -> R) = input?.let { }
        ?: run { callback() }

/**
 * Extension function to execute a [block] when the given [condition] is false.
 */
inline fun whenFalse(condition: Boolean, block: () -> Unit) {
    if (!condition) {
        block()
    }
}

/**
 * Extension function to execute a [block] when the given [condition] is true.
 */
inline fun whenTrue(condition: Boolean, block: () -> Unit) {
    if (condition) {
        block()
    }
}