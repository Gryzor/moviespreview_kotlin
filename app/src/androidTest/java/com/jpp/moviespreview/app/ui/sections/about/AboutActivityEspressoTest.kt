package com.jpp.moviespreview.app.ui.sections.about

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.net.Uri
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.intent.Intents.*
import android.support.test.espresso.intent.matcher.IntentMatchers.*
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.v7.widget.RecyclerView
import com.jpp.moviespreview.BuildConfig
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.extentions.MoviesPreviewActivityTestRule
import com.jpp.moviespreview.app.extentions.launch
import com.jpp.moviespreview.app.ui.sections.about.AboutActivity.Companion.APP_MARKET_URI
import com.jpp.moviespreview.app.utils.RecyclerViewItemCountAssertion
import org.junit.Rule
import org.junit.Test

/**
 * Espresso tests are to verify the UI module.
 *
 * This test exercises the View, the Presenter and the Interactor for the
 * about section.
 *
 * Created by jpp on 1/20/18.
 */
class AboutActivityEspressoTest {

    @get:Rule
    @JvmField
    val activityRule = MoviesPreviewActivityTestRule(AboutActivity::class.java)


    @Test
    fun showsAppVersionAndAboutActions() {
        activityRule.launch(Intent())

        val expectedText = activityRule.activity.getString(R.string.about_version, BuildConfig.VERSION_NAME)

        onView(withId(R.id.about_screen_version_text_view))
                .check(matches(withText(expectedText)))

        onView(withId(R.id.rv_about_screen))
                .check(RecyclerViewItemCountAssertion(4))
    }


    @Test
    fun onRateApplicationSelected() {
        init()

        activityRule.launch(Intent())

        // intercept intent launched since emulator doesn't have the market app
        val intentResult = Instrumentation.ActivityResult(Activity.RESULT_OK, Intent())
        intending(anyIntent()).respondWith(intentResult)

        onView(withId(R.id.rv_about_screen))
                .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        intended(hasAction(Intent.ACTION_VIEW))
        intended(hasData(Uri.parse(String.format("%s?id=%s", APP_MARKET_URI, activityRule.activity.packageName))))
        release()
    }


    @Test
    fun onShareApplicationSelected() {
        init()

        activityRule.launch(Intent())

        // intercept intent launched to avoid blocking the UI with the selection dialog
        val intentResult = Instrumentation.ActivityResult(Activity.RESULT_OK, Intent())
        intending(anyIntent()).respondWith(intentResult)

        onView(withId(R.id.rv_about_screen))
                .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))

        val expectedUrl = activityRule
                .activity
                .getString(R.string.share_app_text,
                        Uri.parse(String.format("%s?id=%s", AboutActivity.APP_WEB_URL, activityRule.activity.packageName)))

        intended(hasAction(Intent.ACTION_SEND))
        intended(hasExtra(Intent.EXTRA_TEXT, expectedUrl))
        intended(hasType("text/plain"))
        release()
    }
}