package com.jpp.moviespreview.app.util.extentions

import android.content.Context
import android.net.ConnectivityManager

/**
 * Verifies if the device is connected to a network.
 */
fun Context.isConnectedToNetwork(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo?.isConnectedOrConnecting ?: false
}


/**
 * Verifies if the device is connected to a network or not. If it is connected
 * to a network, it executes [connectedToNetwork]. If not, executes [notConnectedToNetwork]
 */
fun Context.notConnectedToNetwork(notConnectedToNetwork: () -> Unit?,
                                  connectedToNetwork: () -> Unit?) {
    if (isConnectedToNetwork()) {
        connectedToNetwork()
    } else {
        notConnectedToNetwork()
    }
}