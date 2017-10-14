package com.jpp.moviespreview.app.ui.splash

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.jpp.moviespreview.app.TestComponentRule
import com.jpp.moviespreview.app.ui.MoviesContext
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

/**
 * Created by jpp on 10/13/17.
 */
@RunWith(AndroidJUnit4::class)
class SplashActivityEspressoTest {


    @get:Rule @JvmField
    val activityRule = ActivityTestRule(SplashActivity::class.java)
    @get:Rule
    val testComponentRule = TestComponentRule()


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