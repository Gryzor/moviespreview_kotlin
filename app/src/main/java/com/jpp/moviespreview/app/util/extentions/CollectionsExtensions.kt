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


inline fun <T, R : Any> Iterable<T>.mapIfApplies(transform: (T) -> R): List<R> {
    val listOfResults = ArrayList<R>()
    this.mapTo(listOfResults) { transform(it) }
    return listOfResults
}