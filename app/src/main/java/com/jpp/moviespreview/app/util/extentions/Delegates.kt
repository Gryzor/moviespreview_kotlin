package com.jpp.moviespreview.app.util.extentions

import kotlin.reflect.KProperty

/**
 * Created by jpp on 2/14/18.
 */

/**
 * Defined object to follow the delegates creation convention from the language
 */
object DelegatesExt {

    /**
     * Creates an [ObservableTypedDelegate] of the specified type with the provided
     * [observer] function.
     */
    fun <T> observerDelegate(observer: () -> Unit) = ObservableTypedDelegate<T>(observer)

}


/**
 * A Delegate (Kotlin delegates : https://kotlinlang.org/docs/reference/delegated-properties.html)
 * that executes an action ([observer]) when the property is set.
 *
 * Why don't use the default Observer Delegate provided by Kotlin?
 * Because the default Observer delegate forces type declaration when defining the delegated
 * property and I want this delegate to be defined by the Commands definitions.
 */
class ObservableTypedDelegate<T>(private val observer: () -> Unit) {

    private var value: T? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T? = value

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
        observer()
    }
}