package com.jpp.moviespreview.app.ui.splash

import android.support.test.rule.ActivityTestRule
import android.util.Log
import com.jpp.moviespreview.app.EspressoAppComponent
import com.jpp.moviespreview.app.TestComponentRule
import com.jpp.moviespreview.app.data.server.MoviesPreviewApi
import com.jpp.moviespreview.app.mock
import com.jpp.moviespreview.app.ui.MoviesContext
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

/**
 * Created by jpp on 10/13/17.
 */
class SplashActivityEspressoTest {


    @get:Rule val activityRule = ActivityTestRule(SplashActivity::class.java)
    @get:Rule val testComponentRule = TestComponentRule()


    @Inject
    lateinit var moviesContext: MoviesContext

    @Before
    fun setUp() {
        testComponentRule.testComponent?.inject(this)
    }

    @Test
    fun someRamdomTest() {
        activityRule.launchActivity(null)
        Log.d("Aver", moviesContext.toString())
        Assert.assertEquals(1, 1)
    }

}