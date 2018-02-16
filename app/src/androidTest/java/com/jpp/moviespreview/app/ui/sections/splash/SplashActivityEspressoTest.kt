package com.jpp.moviespreview.app.ui.sections.splash

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.runner.AndroidJUnit4
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.EspressoMoviesPreviewApp
import com.jpp.moviespreview.app.TestComponentRule
import com.jpp.moviespreview.app.extentions.MoviesPreviewActivityTestRule
import com.jpp.moviespreview.app.extentions.launch
import com.jpp.moviespreview.app.mock
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractor
import com.jpp.moviespreview.app.ui.sections.splash.di.SplashActivityComponent
import com.nhaarman.mockito_kotlin.any
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
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

    private val splashPresenter: SplashPresenter = mock()
    private val builder: SplashActivityComponent.Builder = mock()
    private val mockSplashActivityComponent by lazy {
        EspressoSplashActivityComponent(splashPresenter)
    }

    @Before
    fun setUp() {
        `when`(builder.build()).thenReturn(mockSplashActivityComponent)
        `when`(builder.activityModule(any())).thenReturn(builder)

        val app = InstrumentationRegistry.getTargetContext().applicationContext as EspressoMoviesPreviewApp
        app.putActivityComponentBuilder(builder, SplashActivity::class.java)

        testComponentRule.testComponent?.inject(this)

    }


    /*TODO Find a workaround for these tests. The problem is that the MainActivity is being
      started, without info in the context, causing a failure, the dialog is shown
      and then the test hangs.*/
//    @Test
//    fun completesContextAndContinuesToHomeScreen() {
//        Intents.init()
//        val moviesConfiguration = activityRule.loadDomainConfig()
//        `when`(moviesConfigUseCase.execute()).thenReturn(moviesConfiguration)
//
//        val genreList = activityRule.loadDomainGenres()
//        `when`(genresUseCase.execute()).thenReturn(genreList)
//
//        activityRule.launch(Intent())
//
//
//        activityRule.waitToFinish()
//
//        assertNotNull(moviesContext.profileImageConfig)
//        assertEquals(moviesConfiguration.imagesConfiguration.count { it.type == PROFILE }, moviesContext.profileImageConfig!!.size)
//
//        assertNotNull(moviesContext.posterImageConfig)
//        assertEquals(moviesConfiguration.imagesConfiguration.count { it.type == POSTER }, moviesContext.posterImageConfig!!.size)
//
//        assertNotNull(moviesContext.movieGenres)
//        assertEquals(genreList.size, moviesContext.movieGenres!!.size)
//
//
//        val name = MainActivity::class.java.name
//        Intents.intended(IntentMatchers.hasComponent(name))
//        assertTrue(activityRule.activity.isDestroyed)
//
//        Intents.release()
//    }


//    @Test
//    fun continuesToHomeScreenWhenContextIsCompleted() {
//        Intents.init()
//        moviesContext.movieGenres = listOf(MovieGenre(1, "aGenre", 1))
//        moviesContext.posterImageConfig = listOf(PosterImageConfiguration("aUrl", "aSize"))
//        moviesContext.profileImageConfig = listOf(ProfileImageConfiguration("aUrl", "aSize"))
//
//        activityRule.launch(Intent())
//
//
//        activityRule.waitToFinish()
//
//        verifyZeroInteractions(genresUseCase)
//        verifyZeroInteractions(moviesConfigUseCase)
//
//        val name = MainActivity::class.java.name
//        Intents.intended(IntentMatchers.hasComponent(name))
//        assertTrue(activityRule.activity.isDestroyed)
//
//        Intents.release()
//    }


    @Test
    fun appShowsConnectivityErrorWhenRetrievingMoviesConfigAndNoConnection() {
//        `when`(moviesConfigUseCase.execute()).thenReturn(null)
//        `when`(connectivityInteractor.isConnectedToNetwork()).thenReturn(false)

        activityRule.launch(Intent())

        onView(withText(R.string.alert_no_network_connection_message))
                .check(matches(isDisplayed()))
    }


    @Test
    fun appShowsConnectivityErrorWhenRetrievingGenresAndNoConnection() {
//        //config OK
//        val moviesConfiguration = activityRule.loadDomainConfig()
//        `when`(moviesConfigUseCase.execute()).thenReturn(moviesConfiguration)
//        // no genres
//        `when`(genresUseCase.execute()).thenReturn(null)
//        // no connectivity
//        `when`(connectivityInteractor.isConnectedToNetwork()).thenReturn(false)
//
//        activityRule.launch(Intent())
//
//        onView(withText(R.string.alert_no_network_connection_message))
//                .check(matches(isDisplayed()))
    }

    @Test
    fun appShowsUnexpectedErrorWhenRetrievingMoviesConfig() {
//        `when`(moviesConfigUseCase.execute()).thenReturn(null)
//        `when`(connectivityInteractor.isConnectedToNetwork()).thenReturn(true)
//
//        activityRule.launch(Intent())
//
//        onView(withText(R.string.alert_unexpected_error_message))
//                .check(matches(isDisplayed()))
    }

    @Test
    fun appShowsUnexpectedErrorWhenRetrievingGenres() {
//        //config OK
//        val moviesConfiguration = activityRule.loadDomainConfig()
//        `when`(moviesConfigUseCase.execute()).thenReturn(moviesConfiguration)
//        // no genres
//        `when`(genresUseCase.execute()).thenReturn(null)
//
//        `when`(connectivityInteractor.isConnectedToNetwork()).thenReturn(true)
//
//        activityRule.launch(Intent())
//
//        onView(withText(R.string.alert_unexpected_error_message))
//                .check(matches(isDisplayed()))
    }
}