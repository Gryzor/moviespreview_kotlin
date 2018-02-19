package com.jpp.moviespreview.app.domain

import com.jpp.moviespreview.app.util.extentions.DelegatesExt

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
 *
 * Created by jpp on 2/12/18.
 */


/**
 * Represents a data bean that it's used by the [Command] to execute an action
 * and set the result.
 * [valueObserver] will be executed when the [value] of this class is set.
 * [errorObserver] will be executed when the [error] of this class is set.
 */
class CommandData<T>(valueObserver: () -> Unit,
                     errorObserver: () -> Unit) {
    var value: T? by DelegatesExt.observerDelegate(valueObserver)
    var error: Exception? by DelegatesExt.observerDelegate(errorObserver)
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