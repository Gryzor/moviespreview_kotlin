package com.jpp.moviespreview.app.ui.splash

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.WindowManager
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.TestComponentRule
import com.jpp.moviespreview.app.data.ImagesConfiguration
import com.jpp.moviespreview.app.data.MoviesConfiguration
import com.jpp.moviespreview.app.data.cache.MoviesCache
import com.jpp.moviespreview.app.extentions.TimeUtils
import com.jpp.moviespreview.app.extentions.WaitActivityIsResumedIdlingResource
import com.jpp.moviespreview.app.extentions.launch
import com.jpp.moviespreview.app.extentions.waitToFinish
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.SetupScreen
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractor
import com.jpp.moviespreview.app.ui.main.MainActivity
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * Espreso tests are to verify interaction between three components:
 *  1 - Views
 *  2 - Presenters
 *  3 - UseCases
 *
 * Created by jpp on 10/13/17.
 */
@RunWith(AndroidJUnit4::class)
class SplashActivityEspressoTest {


    @get:Rule
    @JvmField
    val activityRule = ActivityTestRule(SplashActivity::class.java)
    @get:Rule
    val testComponentRule = TestComponentRule()


    @Inject
    lateinit var moviesContext: MoviesContext

    @Inject
    lateinit var moviesCache: MoviesCache

    @Inject
    lateinit var timeUtils: TimeUtils

    @Inject
    lateinit var connectivityInteractor: ConnectivityInteractor

    @Before
    fun setUp() {
        testComponentRule.testComponent?.inject(this)
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Test
    fun test_appContinuesToHome_whenConfigurationRestoredFromCache() {
        Intents.init()
        val imagesConfiguration = ImagesConfiguration("someUrl", ArrayList())
        val moviesConfiguration = MoviesConfiguration(imagesConfiguration)

        Mockito.`when`(moviesCache.isLastConfigOlderThan(TimeUnit.MINUTES.toMillis(30), timeUtils)).thenReturn(false)
        Mockito.`when`(moviesCache.getLastMovieConfiguration()).thenReturn(moviesConfiguration)

        activityRule.launch(Intent())

        val name = MainActivity::class.java.name
        val idlingResource = WaitActivityIsResumedIdlingResource(name)
        // Espresso.registerIdlingResources(idlingResource)

        activityRule.waitToFinish()
        Assert.assertNotNull(moviesContext.imageConfig)
        Intents.intended(IntentMatchers.hasComponent(name))
        Assert.assertTrue(activityRule.activity.isDestroyed)
        // Espresso.unregisterIdlingResources(idlingResource)
        Intents.release()
    }


    @Test
    fun test_appShowsConnectivityError() {
        Mockito.`when`(moviesCache.isLastConfigOlderThan(TimeUnit.MINUTES.toMillis(30), timeUtils)).thenReturn(false)
        Mockito.`when`(moviesCache.getLastMovieConfiguration()).thenReturn(null)
        Mockito.`when`(connectivityInteractor.isConnectedToNetwork()).thenReturn(false)

        activityRule.launch(Intent())

        Espresso.onView(ViewMatchers.withText(R.string.movies_preview_alert_no_network_connection_message))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_appShowsUnexpectedError() {
        Mockito.`when`(moviesCache.isLastConfigOlderThan(TimeUnit.MINUTES.toMillis(30), timeUtils)).thenReturn(false)
        Mockito.`when`(moviesCache.getLastMovieConfiguration()).thenReturn(null)
        Mockito.`when`(connectivityInteractor.isConnectedToNetwork()).thenReturn(true)

        activityRule.launch(Intent())

        Espresso.onView(ViewMatchers.withText(R.string.movies_preview_alert_unexpected_error_message))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}