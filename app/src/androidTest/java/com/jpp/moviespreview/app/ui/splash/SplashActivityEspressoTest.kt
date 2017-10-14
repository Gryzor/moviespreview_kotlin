package com.jpp.moviespreview.app.ui.splash

import android.support.test.rule.ActivityTestRule
import com.jpp.moviespreview.app.EspressoAppComponent
import com.jpp.moviespreview.app.data.server.MoviesPreviewApi
import com.jpp.moviespreview.app.mock
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by jpp on 10/13/17.
 */
class SplashActivityEspressoTest {


    @get:Rule val activityRule = ActivityTestRule(SplashActivity::class.java)


    lateinit var mocked: MoviesPreviewApi

    @Before
    fun setUp() {


    }

    @Test
    fun someRamdomTest() {
        mocked = mock()
        activityRule.launchActivity(null)
        Assert.assertEquals(1, 1)
    }

}