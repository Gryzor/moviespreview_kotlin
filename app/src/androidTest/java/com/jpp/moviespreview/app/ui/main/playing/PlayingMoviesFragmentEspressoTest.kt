package com.jpp.moviespreview.app.ui.main.playing

import android.app.Activity
import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.Intents.*
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.TestComponentRule
import com.jpp.moviespreview.app.completeConfig
import com.jpp.moviespreview.app.domain.MoviesInTheaterInputParam
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.extentions.MoviesPreviewActivityTestRule
import com.jpp.moviespreview.app.extentions.launch
import com.jpp.moviespreview.app.extentions.rotate
import com.jpp.moviespreview.app.extentions.waitToFinish
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailActivity
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractor
import com.jpp.moviespreview.app.ui.sections.main.playing.PlayingMoviesFragment
import com.jpp.moviespreview.app.ui.sections.splash.SplashActivity
import com.jpp.moviespreview.app.ui.util.EspressoTestActivity
import com.jpp.moviespreview.app.util.extentions.addFragmentIfNotInStack
import com.jpp.moviespreview.app.utils.RecyclerViewItemCountAssertion
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer
import javax.inject.Inject
import com.jpp.moviespreview.app.domain.MoviePage as DomainMoviePage

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
    val activityRule = MoviesPreviewActivityTestRule(EspressoTestActivity::class.java)
    @get:Rule
    val testComponentRule = TestComponentRule()

    @Inject
    lateinit var moviesContext: MoviesContext
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

        val initialPage = 1
        val expectedMoviePage = activityRule.loadDomainPage(initialPage)

        `when`(playingMoviesUseCase.execute(any(MoviesInTheaterInputParam::class.java)))
                .thenAnswer(MoviePageAnswer(expectedMoviePage, initialPage))


        launchActivityAndAddFragment()

        onView(withId(R.id.rv_playing_movies))
                .check(RecyclerViewItemCountAssertion(20))
    }


    @Test
    fun doesNotRetrievesDataWhenPagesInContext() {
        // complete context configuration
        moviesContext.completeConfig()


        // add 3 pages to the context
        var dataMoviePage = activityRule.loadDomainPage(1)
        var uiMoviePage = DomainToUiDataMapper().convertDomainMoviePageToUiMoviePage(dataMoviePage, moviesContext.posterImageConfig!![0], moviesContext.movieGenres!!)
        moviesContext.addMoviePage(uiMoviePage)

        dataMoviePage = activityRule.loadDomainPage(2)
        uiMoviePage = DomainToUiDataMapper().convertDomainMoviePageToUiMoviePage(dataMoviePage, moviesContext.posterImageConfig!![0], moviesContext.movieGenres!!)
        moviesContext.addMoviePage(uiMoviePage)

        dataMoviePage = activityRule.loadDomainPage(3)
        uiMoviePage = DomainToUiDataMapper().convertDomainMoviePageToUiMoviePage(dataMoviePage, moviesContext.posterImageConfig!![0], moviesContext.movieGenres!!)
        moviesContext.addMoviePage(uiMoviePage)

        launchActivityAndAddFragment()

        // verify all pages where loaded
        onView(withId(R.id.rv_playing_movies))
                .check(RecyclerViewItemCountAssertion(60))

        verifyZeroInteractions(playingMoviesUseCase)
    }


    @Test
    fun orientationChangeDoesNotRequestNewData() {
        // complete context configuration
        moviesContext.completeConfig()

        val initialPage = 1
        val expectedMoviePage = activityRule.loadDomainPage(initialPage)

        `when`(playingMoviesUseCase.execute(any(MoviesInTheaterInputParam::class.java)))
                .thenAnswer(MoviePageAnswer(expectedMoviePage, initialPage))

        launchActivityAndAddFragment()

        // sanity check
        onView(withId(R.id.rv_playing_movies))
                .check(RecyclerViewItemCountAssertion(20))

        // rotate 1
        activityRule.rotate()
        onView(withId(R.id.rv_playing_movies))
                .check(RecyclerViewItemCountAssertion(20))

        // rotate 2
        activityRule.rotate()
        onView(withId(R.id.rv_playing_movies))
                .check(RecyclerViewItemCountAssertion(20))

        verify(playingMoviesUseCase).execute(any(MoviesInTheaterInputParam::class.java))
    }


    @Test
    fun appShowsConnectivityErrorWhenRetrievingDataAndNoConnection() {
        // complete context configuration
        moviesContext.completeConfig()

        // return null page will cause error verification
        `when`(playingMoviesUseCase.execute(any(MoviesInTheaterInputParam::class.java))).thenReturn(null)

        // mock connectivity issues
        `when`(connectivityInteractor.isConnectedToNetwork()).thenReturn(false)

        launchActivityAndAddFragment()

        onView(withText(R.string.alert_no_network_connection_message))
                .check(ViewAssertions.matches(isDisplayed()))

    }

    @Test
    fun appShowsUnexpectedError() {
        // complete context configuration
        moviesContext.completeConfig()

        // return null page will cause error verification
        `when`(playingMoviesUseCase.execute(any(MoviesInTheaterInputParam::class.java))).thenReturn(null)

        // mock connectivity NO issues
        `when`(connectivityInteractor.isConnectedToNetwork()).thenReturn(true)

        launchActivityAndAddFragment()

        onView(withText(R.string.alert_unexpected_error_message))
                .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun userSelectsMovieSetsMovieInContextStartDetailsActivity() {
        init()

        // complete context configuration
        moviesContext.completeConfig()

        val initialPage = 1
        val expectedMoviePage = activityRule.loadDomainPage(initialPage)
        val expectedSelectedMovie = expectedMoviePage.results[3]

        `when`(playingMoviesUseCase.execute(any(MoviesInTheaterInputParam::class.java)))
                .thenAnswer(MoviePageAnswer(expectedMoviePage, initialPage))

        launchActivityAndAddFragment()

        onView(withId(R.id.rv_playing_movies))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))

        assertNotNull(moviesContext.selectedMovie)
        assertEquals(moviesContext.selectedMovie!!.id, expectedSelectedMovie.id)

        intended(hasComponent(MovieDetailActivity::class.java.name))
        release()
    }


    private fun launchActivityAndAddFragment() {
        activityRule.launch(Intent())
        activityRule.activity.addFragmentIfNotInStack(android.R.id.content, PlayingMoviesFragment.newInstance(), PlayingMoviesFragment.TAG)
    }

    private class MoviePageAnswer(private val expectedMoviePage: DomainMoviePage,
                                  private val initialPage: Int? = null) : Answer<DomainMoviePage> {
        override fun answer(invocation: InvocationOnMock?): DomainMoviePage? {
            val argument = invocation!!.arguments[0]
            return if (argument is MoviesInTheaterInputParam) {
                val page = argument.page
                if (initialPage != null) {
                    assertEquals(initialPage, page)
                }
                return expectedMoviePage
            } else {
                null
            }
        }
    }


    private class MoviePageAnswerLoader<T : Activity>(private val activityTestRule: MoviesPreviewActivityTestRule<T>) : Answer<DomainMoviePage> {
        override fun answer(invocation: InvocationOnMock?): DomainMoviePage? {
            val argument = invocation!!.arguments[0]
            return if (argument is MoviesInTheaterInputParam) {
                val page = argument.page
                return activityTestRule.loadDomainPage(page)
            } else {
                null
            }
        }
    }

}