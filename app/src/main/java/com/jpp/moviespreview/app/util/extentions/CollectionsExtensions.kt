package com.jpp.moviespreview.app.util.extentions

/**
 * Filters this current list by iterating on [listIn] and
 * verifying if [predicate] is fulfilled. If it is, the item
 * is added to the result.
 */
inline fun <T, R> Iterable<T>.filterInList(listIn: List<R>,
                                           predicate: (T, R) -> Boolean): List<T> {

    val result = ArrayList<T>()
    for (item in this) {
        listIn
                .filter { predicate(item, it) }
                .forEach { result.add(item) }
    }
    return result
}


/**
 * Adds the entire [list] provided to this list.
 */
fun <T> MutableList<T>.addList(list: List<T>): List<T> {
    for (element in list) {
        this.add(element)
    }
    return this
}

/**
 * Retrieves the position if the current element fulfills the [criteria].
 * If no element fulfills the [criteria], a NoSuchElementException is thrown.
 */
inline fun <T> MutableList<T>.getPositionForElement(criteria: (T) -> Boolean): Int {
    var index = 0
    this
            .filter { criteria(it) }
            .forEach { return index++ }
    throw NoSuchElementException()
}