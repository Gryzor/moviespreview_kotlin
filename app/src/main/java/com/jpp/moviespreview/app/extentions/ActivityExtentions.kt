package com.jpp.moviespreview.app.extentions

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.MoviesPreviewApp
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton


/**
 * Provides access to application context (as MoviesPreviewApp)
 */
val Activity.app: MoviesPreviewApp
    get() = application as MoviesPreviewApp


/**
 * Verifies if the device is connected to a network.
 */
fun Activity.isConnectedToNetwork(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo?.isConnectedOrConnecting ?: false
}


/**
 * Verifies if the device is connected to a network or not. If it is connected
 * to a network, it executes [connectedToNetwork]. If not, executes [notConnectedToNetwork]
 */
fun Activity.notConnectedToNetwork(notConnectedToNetwork: () -> Unit?,
                                   connectedToNetwork: () -> Unit?) {
    if (isConnectedToNetwork()) {
        connectedToNetwork()
    } else {
        notConnectedToNetwork()
    }
}

/**
 * Shows a no network connection alert dialog and closes the application
 * when the user preses the OK button.
 */
fun Activity.showNoNetworkConnectionAlert() {
    alert(getString(R.string.movies_preview_alert_no_network_connection_message)) {
        title = getString(R.string.app_name)
        yesButton { finish() }
    }.show()
}

/**
 * Shows an alert dialog showing an unexpected generic error message.
 * When the user preses the OK button, [actionOnOkButton] is executed.
 */
fun Activity.showUnexpectedError(actionOnOkButton: () -> Unit?) {
    alert(getString(R.string.movies_preview_alert_unexpected_error_message)) {
        title = getString(R.string.app_name)
        yesButton { actionOnOkButton() }
    }.show()
}