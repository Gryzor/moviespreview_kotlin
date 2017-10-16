package com.jpp.moviespreview.app.extentions

import android.app.Activity
import android.content.Intent
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.lifecycle.ActivityLifecycleCallback
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import android.support.test.runner.lifecycle.Stage
import java.util.concurrent.CountDownLatch

/**
 * Created by jpp on 10/14/17.
 */
fun <T : Activity> ActivityTestRule<T>.launch(intent: Intent? = null) {
    launchActivity(intent)
}


fun <T : Activity> ActivityTestRule<T>.waitToFinish() {
    val waitForFinished = WaitForFinished()
    runOnUiThread {
        waitForFinished.register()
    }
    waitForFinished.awaitResumed()
    runOnUiThread { waitForFinished.unregister() }
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