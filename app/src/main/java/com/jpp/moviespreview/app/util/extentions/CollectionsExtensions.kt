package com.jpp.moviespreview.app.util.extentions

/**
 * Filters this current list by iterating on [listIn] and
 * verifying if [predicate] is fulfilled. If it is, the item
 * is added to the result.
 */
public inline fun <T, R> Iterable<T>.filterInList(listIn: List<R>,
                                                  predicate: (T, R) -> Boolean): List<T> {

    val result = ArrayList<T>()
    for (item in this) {
        listIn
                .filter { predicate(item, it) }
                .forEach { result.add(item) }
    }
    return result
}
