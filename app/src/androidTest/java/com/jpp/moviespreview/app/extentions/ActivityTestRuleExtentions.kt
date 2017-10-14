package com.jpp.moviespreview.app.extentions

import android.app.Activity
import android.content.Intent
import android.support.test.rule.ActivityTestRule

/**
 * Created by jpp on 10/14/17.
 */
inline fun <T : Activity> ActivityTestRule<T>.launch(intent: Intent? = null) {
    launchActivity(intent)
}