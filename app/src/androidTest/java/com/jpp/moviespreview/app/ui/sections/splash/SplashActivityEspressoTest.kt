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
import com.jpp.moviespreview.app.extentions.MoviesPreviewActivityTestRule
import com.jpp.moviespreview.app.extentions.launch
import com.jpp.moviespreview.app.mock
import com.jpp.moviespreview.app.ui.sections.splash.di.SplashActivityComponent
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import com.jpp.moviespreview.app.domain.Genre as DomainGenre
import com.jpp.moviespreview.app.domain.MoviesConfiguration as DomainMoviesConfiguration


/**
 * Espresso tests to exercise [SplashActivity].
 *
 * Created by jpp on 10/13/17.
 */
@RunWith(AndroidJUnit4::class)
class SplashActivityEspressoTest {


    @get:Rule
    @JvmField
    val activityRule = MoviesPreviewActivityTestRule(SplashActivity::class.java)

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

    }


    @Test
    fun appShowsConnectivityError() {
        doAnswer {
            val viewInstance = it.arguments[0] as SplashView
            viewInstance.showNotConnectedToNetwork()
        }.`when`(splashPresenter).linkView(any())

        activityRule.launch(Intent())

        onView(withText(R.string.alert_no_network_connection_message))
                .check(matches(isDisplayed()))
    }


    @Test
    fun appShowsUnexpectedError() {
        doAnswer {
            val viewInstance = it.arguments[0] as SplashView
            viewInstance.showUnexpectedError()
        }.`when`(splashPresenter).linkView(any())

        activityRule.launch(Intent())

        onView(withText(R.string.alert_unexpected_error_message))
                .check(matches(isDisplayed()))
    }


    /**
     * Provides injection for [SplashPresenter]
     *
     * Created by jpp on 2/14/18.
     */
    class EspressoSplashActivityComponent(private val splashPresenterInstance: SplashPresenter) : SplashActivityComponent {
        override fun injectMembers(instance: SplashActivity?) {
            instance?.let {
                it.splashPresenter = splashPresenterInstance
            }
        }
    }
}