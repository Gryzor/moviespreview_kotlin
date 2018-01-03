package com.jpp.moviespreview.app.ui.splash

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.runner.AndroidJUnit4
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.TestComponentRule
import com.jpp.moviespreview.app.domain.ImageConfiguration.Companion.POSTER
import com.jpp.moviespreview.app.domain.ImageConfiguration.Companion.PROFILE
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.extentions.MoviesPreviewActivityTestRule
import com.jpp.moviespreview.app.extentions.launch
import com.jpp.moviespreview.app.extentions.waitToFinish
import com.jpp.moviespreview.app.ui.MovieGenre
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.PosterImageConfiguration
import com.jpp.moviespreview.app.ui.ProfileImageConfiguration
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractor
import com.jpp.moviespreview.app.ui.sections.main.MainActivity
import com.jpp.moviespreview.app.ui.sections.splash.SplashActivity
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verifyZeroInteractions
import javax.inject.Inject
import com.jpp.moviespreview.app.domain.Genre as DomainGenre
import com.jpp.moviespreview.app.domain.MoviesConfiguration as DomainMoviesConfiguration


/**
 * Espresso tests are to verify the UI module.
 *
 * This test exercises the View, the Presenter and the Mapper for the
 * Splash section.
 *
 * Created by jpp on 10/13/17.
 */
@RunWith(AndroidJUnit4::class)
class SplashActivityEspressoTest {


    @get:Rule
    @JvmField
    val activityRule = MoviesPreviewActivityTestRule(SplashActivity::class.java)
    @get:Rule
    val testComponentRule = TestComponentRule()


    @Inject
    lateinit var moviesContext: MoviesContext
    @Inject
    lateinit var connectivityInteractor: ConnectivityInteractor
    @Inject
    lateinit var moviesConfigUseCase: UseCase<Any, DomainMoviesConfiguration>
    @Inject
    lateinit var genresUseCase: UseCase<Any, List<DomainGenre>>

    @Before
    fun setUp() {
        testComponentRule.testComponent?.inject(this)
    }


    @Test
    fun completesContextAndContinuesToHomeScreen() {
        Intents.init()
        val moviesConfiguration = activityRule.loadDomainConfig()
        `when`(moviesConfigUseCase.execute()).thenReturn(moviesConfiguration)

        val genreList = activityRule.loadDomainGenres()
        `when`(genresUseCase.execute()).thenReturn(genreList)

        activityRule.launch(Intent())


        activityRule.waitToFinish()

        assertNotNull(moviesContext.profileImageConfig)
        assertEquals(moviesConfiguration.imagesConfiguration.count { it.type == PROFILE }, moviesContext.profileImageConfig!!.size)

        assertNotNull(moviesContext.posterImageConfig)
        assertEquals(moviesConfiguration.imagesConfiguration.count { it.type == POSTER }, moviesContext.posterImageConfig!!.size)

        assertNotNull(moviesContext.movieGenres)
        assertEquals(genreList.size, moviesContext.movieGenres!!.size)


        val name = MainActivity::class.java.name
        Intents.intended(IntentMatchers.hasComponent(name))
        assertTrue(activityRule.activity.isDestroyed)

        Intents.release()
    }


    @Test
    fun continuesToHomeScreenWhenContextIsCompleted() {
        Intents.init()
        moviesContext.movieGenres = listOf(MovieGenre(1, "aGenre", 1))
        moviesContext.posterImageConfig = listOf(PosterImageConfiguration("aUrl", "aSize"))
        moviesContext.profileImageConfig = listOf(ProfileImageConfiguration("aUrl", "aSize"))

        activityRule.launch(Intent())


        activityRule.waitToFinish()

        verifyZeroInteractions(genresUseCase)
        verifyZeroInteractions(moviesConfigUseCase)

        val name = MainActivity::class.java.name
        Intents.intended(IntentMatchers.hasComponent(name))
        assertTrue(activityRule.activity.isDestroyed)

        Intents.release()
    }


    @Test
    fun appShowsConnectivityErrorWhenRetrievingMoviesConfigAndNoConnection() {
        `when`(moviesConfigUseCase.execute()).thenReturn(null)
        `when`(connectivityInteractor.isConnectedToNetwork()).thenReturn(false)

        activityRule.launch(Intent())

        onView(withText(R.string.alert_no_network_connection_message))
                .check(matches(isDisplayed()))
    }


    @Test
    fun appShowsConnectivityErrorWhenRetrievingGenresAndNoConnection() {
        //config OK
        val moviesConfiguration = activityRule.loadDomainConfig()
        `when`(moviesConfigUseCase.execute()).thenReturn(moviesConfiguration)
        // no genres
        `when`(genresUseCase.execute()).thenReturn(null)
        // no connectivity
        `when`(connectivityInteractor.isConnectedToNetwork()).thenReturn(false)

        activityRule.launch(Intent())

        onView(withText(R.string.alert_no_network_connection_message))
                .check(matches(isDisplayed()))
    }

    @Test
    fun appShowsUnexpectedErrorWhenRetrievingMoviesConfig() {
        `when`(moviesConfigUseCase.execute()).thenReturn(null)
        `when`(connectivityInteractor.isConnectedToNetwork()).thenReturn(true)

        activityRule.launch(Intent())

        onView(withText(R.string.alert_unexpected_error_message))
                .check(matches(isDisplayed()))
    }

    @Test
    fun appShowsUnexpectedErrorWhenRetrievingGenres() {
        //config OK
        val moviesConfiguration = activityRule.loadDomainConfig()
        `when`(moviesConfigUseCase.execute()).thenReturn(moviesConfiguration)
        // no genres
        `when`(genresUseCase.execute()).thenReturn(null)

        `when`(connectivityInteractor.isConnectedToNetwork()).thenReturn(true)

        activityRule.launch(Intent())

        onView(withText(R.string.alert_unexpected_error_message))
                .check(matches(isDisplayed()))
    }
}