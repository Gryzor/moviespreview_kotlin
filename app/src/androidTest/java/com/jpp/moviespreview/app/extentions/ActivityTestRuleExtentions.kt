package com.jpp.moviespreview.app.extentions

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.lifecycle.ActivityLifecycleCallback
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import android.support.test.runner.lifecycle.Stage
import java.util.concurrent.CountDownLatch

/**
 * Extension functions for Activity testing
 * Created by jpp on 10/14/17.
 */

/**
 * Launches a new activity with the provided [intent]. If no [intent] is provided,
 * null intent is passed to Espresso.
 */
fun <T : Activity> ActivityTestRule<T>.launch(intent: Intent? = null) {
    launchActivity(intent)
}


/**
 * Waits until the Activity is finished in order to continue with the execution
 * flow. It does this by attaching an [ActivityLifecycleCallback] to the ongoing
 * Activity and blocking the execution thread (of the test) until the [Activity#onDestroy()]
 * method is called.
 */
fun <T : Activity> ActivityTestRule<T>.waitToFinish() {
    val waitForFinished = WaitForFinished()
    runOnUiThread {
        waitForFinished.register()
    }
    waitForFinished.awaitResumed()
    runOnUiThread { waitForFinished.unregister() }
}


/**
 * Rotates the current [Activity] under test from the current orientation to the alternate.
 * This means that if the activity is in portrait, it's rotated to landscape and viceversa.
 */
fun <T : Activity> ActivityTestRule<T>.rotate() {
    val waitForResumed = WaitForResumed()
    runOnUiThread {
        val currentOrientation = activity.requestedOrientation

        var targetOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        if (currentOrientation == targetOrientation) {
            targetOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        if (targetOrientation == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
            throw IllegalStateException("Unspecified orientation not allowed")
        }

        waitForResumed.register()

        activity.requestedOrientation = targetOrientation
    }

    waitForResumed.awaitResumed()

    waitForResumed.unregister()
}


private class WaitForFinished : ActivityLifecycleCallback {

    val mCountdownLatch = CountDownLatch(1)

    override fun onActivityLifecycleChanged(activity: Activity?, stage: Stage?) {
        if (stage != Stage.DESTROYED) {
            return
        }
        mCountdownLatch.countDown()
    }

    fun register() {
        ActivityLifecycleMonitorRegistry.getInstance().addLifecycleCallback(this)
    }

    fun unregister() {
        ActivityLifecycleMonitorRegistry.getInstance().removeLifecycleCallback(this)
    }

    fun awaitResumed() {
        mCountdownLatch.await()
    }
}


private class WaitForResumed : ActivityLifecycleCallback {
    val mCountdownLatch = CountDownLatch(1)

    override fun onActivityLifecycleChanged(activity: Activity?, stage: Stage?) {
        if (stage != Stage.RESUMED) {
            return
        }
        mCountdownLatch.countDown()
    }

    fun register() {
        ActivityLifecycleMonitorRegistry.getInstance().addLifecycleCallback(this)
    }

    fun unregister() {
        ActivityLifecycleMonitorRegistry.getInstance().removeLifecycleCallback(this)
    }

    fun awaitResumed() {
        mCountdownLatch.await()
    }

}