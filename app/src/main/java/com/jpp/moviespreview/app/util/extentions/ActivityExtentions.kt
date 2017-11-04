package com.jpp.moviespreview.app.util.extentions

import android.app.Activity
import android.graphics.Point
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
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


/**
 * Returns a [Point] in which the x value represents the width of the screen in pixels
 * and the y values represents the height of the screen in pixels.
 */
fun Activity.getScreenSizeInPixels(): Point {
    val display = windowManager.defaultDisplay
    val size = Point()
    display.getSize(size)
    return size
}


/**
 * Adds the provided [Fragment] into the fragment stack (if it was not in the stack) and shows it
 * into the provided [resId].
 */
fun FragmentActivity.addFragmentIfNotInStack(@IdRes resId: Int, fragment: Fragment, tag: String?) {
    if (supportFragmentManager.findFragmentByTag(tag) == null) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(resId, fragment, tag)
        transaction.commit()
    }
}