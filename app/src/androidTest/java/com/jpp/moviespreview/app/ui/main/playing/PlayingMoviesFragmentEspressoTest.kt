package com.jpp.moviespreview.app.ui.main.playing

import android.content.Intent
import android.support.test.espresso.Espresso
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.TestComponentRule
import com.jpp.moviespreview.app.data.cache.MoviesCache
import com.jpp.moviespreview.app.domain.movie.RetrieveMoviesInTheaterUseCase
import com.jpp.moviespreview.app.extentions.launch
import com.jpp.moviespreview.app.extentions.rotate
import com.jpp.moviespreview.app.extentions.waitToFinish
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.splash.SplashActivity
import com.jpp.moviespreview.app.DataPageStubs
import com.jpp.moviespreview.app.ui.util.EspressoTestActivity
import com.jpp.moviespreview.app.completeConfig
import com.jpp.moviespreview.app.stubDataMoviePage
import com.jpp.moviespreview.app.util.extentions.addFragmentIfNotInStack
import com.jpp.moviespreview.app.utils.RecyclerViewItemCountAssertion
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import javax.inject.Inject

/**
 * Espresso test to verify interaction between components:
 *  1 - [PlayingMoviesFragment] / [PlayingMoviesView]
 *  2 - [PlayingMoviesPresenter]
 *  3 - [RetrieveMoviesInTheaterUseCase]
 * Created by jpp on 11/2/17.
 */
@RunWith(AndroidJUnit4::class)
class PlayingMoviesFragmentEspressoTest {


    @get:Rule
    @JvmField
    val activityRule = ActivityTestRule(EspressoTestActivity::class.java)
    @get:Rule
    val testComponentRule = TestComponentRule()

    @Inject
    lateinit var moviesContext: MoviesContext
    @Inject
    lateinit var moviesCache: MoviesCache


    @Before
    fun setUp() {
        testComponentRule.testComponent?.inject(this)
    }


    @Test
    fun test_appGoesBackToSplashScreen_whenConfigIsNotCompleted() {
        // pre:  moviesContext has not been configured
        Intents.init()

        launchActivityAndAddFragment()

        activityRule.waitToFinish()
        Intents.intended(IntentMatchers.hasComponent(SplashActivity::class.java.name))
        Intents.release()
    }

    @Test
    fun test_retrievesInitialPage_whenNoPagesInContextAtStartup() {
        // complete context configuration
        moviesContext.completeConfig()

        // mock movies response
        `when`(moviesCache.isMoviePageOutOfDate(1)).thenReturn(false)
        `when`(moviesCache.getMoviePage(1)).thenReturn(DataPageStubs.Companion.stubDataMoviePage(1, 13, 13))

        launchActivityAndAddFragment()

        Espresso.onView(ViewMatchers.withId(R.id.rv_playing_movies)).check(RecyclerViewItemCountAssertion(3))
    }

    @Test
    fun test_orientationChange_doesNotRequestNewData() {
        // complete context configuration
        moviesContext.completeConfig()

        // mock movies response
        `when`(moviesCache.isMoviePageOutOfDate(1)).thenReturn(false)
        `when`(moviesCache.getMoviePage(1)).thenReturn(DataPageStubs.Companion.stubDataMoviePage(1, 13, 13))

        launchActivityAndAddFragment()

        // sanity check
        Espresso.onView(ViewMatchers.withId(R.id.rv_playing_movies)).check(RecyclerViewItemCountAssertion(3))

        // rotate 1
        activityRule.rotate()
        Espresso.onView(ViewMatchers.withId(R.id.rv_playing_movies)).check(RecyclerViewItemCountAssertion(3))

        // rotate 2
        activityRule.rotate()
        Espresso.onView(ViewMatchers.withId(R.id.rv_playing_movies)).check(RecyclerViewItemCountAssertion(3))

        verify(moviesCache, times(1)).isMoviePageOutOfDate(1)
    }


    private fun launchActivityAndAddFragment() {
        activityRule.launch(Intent())
        activityRule.activity.addFragmentIfNotInStack(android.R.id.content, PlayingMoviesFragment.newInstance(), PlayingMoviesFragment.TAG)
    }

}