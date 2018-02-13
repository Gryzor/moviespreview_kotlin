package com.jpp.moviespreview.app.domain

import kotlin.reflect.KProperty

/**
 * Contract definition for the commands executed by the domain module.
 *
 * Follows the Command/Query separation pattern
 * (https://hackernoon.com/oo-tricks-the-art-of-command-query-separation-9343e50a3de0)
 * by defining a [Command] that alters the state of the system (represented by a
 * [CommandData]) and does not provides eny result data.
 * The [CommandData] defines a signature in which the clients of the framework are notified
 * when the values class values are set, using a delegation pattern.
 *
 * Resuming:
 * [Command] - Executes an action that alters the state of the system.
 * [CommandData] - Defines the system that the Commands can alter.
 * [CommandDelegate] - Delegation mechanism to notify any clients of the framework when the
 *                     system is changed.
 *
 * Created by jpp on 2/12/18.
 */


/**
 * A Delegate (Kotlin delegates : https://kotlinlang.org/docs/reference/delegated-properties.html)
 * that executes an action ([observer]) when the property is set.
 *
 * Why don't use the default Observer Delegate provided by Kotlin?
 * Because the default Observer delegate forces type declaration when defining the delegated
 * property and I want this delegate to be defined by the Commands definitions.
 */
class CommandDelegate<T>(private val observer: () -> Unit) {

    private var value: T? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T =
            value ?: throw UnsupportedOperationException("Property not set")

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
        observer()
    }
}


/**
 * Represents a data bean that it's used by the [Command] to execute an action
 * and set the result.
 * [valueObserver] will be executed when the [value] of this class is set.
 * [errorObserver] will be executed when the [error] of this class is set.
 */
class CommandData<T>(valueObserver: () -> Unit,
                     errorObserver: () -> Unit) {
    var value: T by CommandDelegate(valueObserver)
    var error: Exception by CommandDelegate(errorObserver)
}

/**
 * Command definition to change the state of the provided [CommandData]
 */
interface Command<in Param, Data> {

    /**
     * Executes the command with the provided [param] and sets
     * the result (either OK or Error) into the [data].
     */
    fun execute(data: CommandData<Data>,
                param: Param? = null)
}