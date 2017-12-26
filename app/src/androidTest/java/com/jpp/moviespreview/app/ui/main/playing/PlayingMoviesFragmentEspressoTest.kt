package com.jpp.moviespreview.app.ui.main.playing

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.Intents.*
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.TestComponentRule
import com.jpp.moviespreview.app.completeConfig
import com.jpp.moviespreview.app.data.MoviePage
import com.jpp.moviespreview.app.data.cache.MoviesCache
import com.jpp.moviespreview.app.domain.MoviesInTheaterInputParam
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.domain.movie.MovieDataMapper
import com.jpp.moviespreview.app.extentions.launch
import com.jpp.moviespreview.app.extentions.rotate
import com.jpp.moviespreview.app.extentions.waitToFinish
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.detail.MovieDetailActivity
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractor
import com.jpp.moviespreview.app.ui.splash.SplashActivity
import com.jpp.moviespreview.app.ui.util.EspressoTestActivity
import com.jpp.moviespreview.app.util.extentions.addFragmentIfNotInStack
import com.jpp.moviespreview.app.utils.RecyclerViewItemCountAssertion
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import javax.inject.Inject
import com.jpp.moviespreview.app.domain.MoviePage as DomainMoviePage
//import com.nhaarman.mockito_kotlin.argumentCaptor

/**
 * Espresso tests are to verify the UI module.
 *
 * This test exercises the View, the Presenter and the Mapper for the
 * Playing movies section.
 *
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
    @Inject
    lateinit var connectivityInteractor: ConnectivityInteractor
    @Inject
    lateinit var playingMoviesUseCase: UseCase<MoviesInTheaterInputParam, DomainMoviePage>


    @Before
    fun setUp() {
        testComponentRule.testComponent?.inject(this)
    }


    @Test
    fun goesBackToSplashScreenWhenConfigIsNotCompleted() {
        // pre:  moviesContext has not been configured
        init()

        launchActivityAndAddFragment()

        activityRule.waitToFinish()
        intended(hasComponent(SplashActivity::class.java.name))
        release()
    }

    @Test
    fun retrievesInitialPageWhenNoPagesInContextAtStartup() {
        // complete context configuration
        moviesContext.completeConfig()

        // mock movies response
//        val inputParamCaptor = argumentCaptor<MoviesInTheaterInputParam>()
//        `when`(playingMoviesUseCase.execute(inputParamCaptor.capture())).thenReturn()


        launchActivityAndAddFragment()

        onView(withId(R.id.rv_playing_movies))
                .check(RecyclerViewItemCountAssertion(20))
    }
//
//    @Test
//    fun test_orientationChange_doesNotRequestNewData() {
//        // complete context configuration
//        moviesContext.completeConfig()
//
//        // mock movies response
//        `when`(moviesCache.isMoviePageOutOfDate(1)).thenReturn(false)
//        `when`(moviesCache.getMoviePage(1)).thenReturn(loadMockPage(1))
//
//        launchActivityAndAddFragment()
//
//        // sanity check
//        onView(withId(R.id.rv_playing_movies))
//                .check(RecyclerViewItemCountAssertion(20))
//
//        // rotate 1
//        activityRule.rotate()
//        onView(withId(R.id.rv_playing_movies))
//                .check(RecyclerViewItemCountAssertion(20))
//
//        // rotate 2
//        activityRule.rotate()
//        onView(withId(R.id.rv_playing_movies))
//                .check(RecyclerViewItemCountAssertion(20))
//
//        verify(moviesCache).isMoviePageOutOfDate(1)
//    }
//
//
//    @Test
//    fun test_appShowsConnectivityError_whenRetrievingData_andNoConnection() {
//        // complete context configuration
//        moviesContext.completeConfig()
//
//        // return null page will cause error verification
//        `when`(moviesCache.isMoviePageOutOfDate(1)).thenReturn(false)
//        `when`(moviesCache.getMoviePage(1)).thenReturn(null)
//
//        // mock connectivity issues
//        `when`(connectivityInteractor.isConnectedToNetwork()).thenReturn(false)
//
//        launchActivityAndAddFragment()
//
//        onView(withText(R.string.alert_no_network_connection_message))
//                .check(ViewAssertions.matches(isDisplayed()))
//
//    }
//
//    @Test
//    fun test_appShowsUnexpectedError() {
//        // complete context configuration
//        moviesContext.completeConfig()
//
//        // return null page will cause error verification
//        `when`(moviesCache.isMoviePageOutOfDate(1)).thenReturn(false)
//        `when`(moviesCache.getMoviePage(1)).thenReturn(null)
//
//        // mock connectivity NO issues
//        `when`(connectivityInteractor.isConnectedToNetwork()).thenReturn(true)
//
//        launchActivityAndAddFragment()
//
//        onView(withText(R.string.alert_unexpected_error_message))
//                .check(ViewAssertions.matches(isDisplayed()))
//    }
//
//    @Test
//    fun test_userSelectsMovie_setsMovieInContext_startDetailsActivity() {
//        init()
//
//        // complete context configuration
//        moviesContext.completeConfig()
//
//        // mock movies response
//        `when`(moviesCache.isMoviePageOutOfDate(1)).thenReturn(false)
//        `when`(moviesCache.getMoviePage(1)).thenReturn(loadMockPage(1))
//
//        launchActivityAndAddFragment()
//
//        onView(withId(R.id.rv_playing_movies))
//                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))
//
//        assertNotNull(moviesContext.selectedMovie)
//        assertEquals(moviesContext.selectedMovie!!.id, 335984.toDouble())
//
//        intended(hasComponent(MovieDetailActivity::class.java.name))
//        release()
//    }
//
//
    private fun launchActivityAndAddFragment() {
        activityRule.launch(Intent())
        activityRule.activity.addFragmentIfNotInStack(android.R.id.content, PlayingMoviesFragment.newInstance(), PlayingMoviesFragment.TAG)
    }

//
//    private fun loadDomainPage(page: Int): DomainMoviePage = when(page) {
//        1 -> MovieDataMapper().convertDataMoviePageIntoDomainMoviePage(loadDataPage(page))
//        2 -> activityRule.loadObjectFromJsonFile("data_movie_page_2.json")
//        3 -> activityRule.loadObjectFromJsonFile("data_movie_page_3.json")
//        else -> {
//            throw RuntimeException("Unsupported page requested in test. Page { $page }")
//        }
//    }
//
//    private fun loadDataPage(page: Int): MoviePage = when (page) {
//        1 -> activityRule.loadObjectFromJsonFile("data_movie_page_1.json")
//        2 -> activityRule.loadObjectFromJsonFile("data_movie_page_2.json")
//        3 -> activityRule.loadObjectFromJsonFile("data_movie_page_3.json")
//        else -> {
//            throw RuntimeException("Unsupported page requested in test. Page { $page }")
//        }
//    }

}