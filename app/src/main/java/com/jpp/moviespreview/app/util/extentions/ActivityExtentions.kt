package com.jpp.moviespreview.app.util.extentions

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