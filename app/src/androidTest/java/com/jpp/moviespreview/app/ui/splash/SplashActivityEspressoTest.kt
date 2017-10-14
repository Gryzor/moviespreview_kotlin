package com.jpp.moviespreview.app.ui.splash

import android.content.ComponentName
import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.jpp.moviespreview.app.TestComponentRule
import com.jpp.moviespreview.app.data.ImagesConfiguration
import com.jpp.moviespreview.app.data.MoviesConfiguration
import com.jpp.moviespreview.app.data.cache.MoviesCache
import com.jpp.moviespreview.app.extentions.TimeUtils
import com.jpp.moviespreview.app.extentions.WaitActivityIsResumedIdlingResource
import com.jpp.moviespreview.app.extentions.launch
import com.jpp.moviespreview.app.ui.MoviesContext
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

    @Before
    fun setUp() {
        testComponentRule.testComponent?.inject(this)
    }

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
        Espresso.registerIdlingResources(idlingResource)

        Assert.assertNotNull(moviesContext.imageConfig)
        Intents.intended(IntentMatchers.hasComponent(name))
        Espresso.unregisterIdlingResources(idlingResource)
        Intents.release()
    }
}