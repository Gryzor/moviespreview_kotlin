package com.jpp.moviespreview.app.ui.sections.main.movies

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.runner.AndroidJUnit4
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.EspressoMoviesPreviewApp
import com.jpp.moviespreview.app.extentions.MoviesPreviewActivityTestRule
import com.jpp.moviespreview.app.extentions.launch
import com.jpp.moviespreview.app.mock
import com.jpp.moviespreview.app.mockMovieGenres
import com.jpp.moviespreview.app.mockPosterImageConfig
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.sections.main.movies.di.MoviesFragmentComponent
import com.jpp.moviespreview.app.ui.util.EspressoTestActivity
import com.jpp.moviespreview.app.util.extentions.addFragmentIfNotInStack
import com.jpp.moviespreview.app.util.extentions.whenNotNull
import com.jpp.moviespreview.app.utils.RecyclerViewItemCountAssertion
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Espresso tests for [MoviesFragment]
 *
 * Created by jpp on 2/27/18.
 */
@RunWith(AndroidJUnit4::class)
class MoviesFragmentEspressoTest {

    @get:Rule
    @JvmField
    val activityRule = MoviesPreviewActivityTestRule(EspressoTestActivity::class.java)

    private val moviesPresenter: MoviesPresenter = mock()
    private val builder: MoviesFragmentComponent.Builder = mock()
    private val mockMoviesFragmentComponenet by lazy {
        EspressoMoviesFragmentComponent(moviesPresenter)
    }


    @Before
    fun setUp() {
        whenever(builder.build()).thenReturn(mockMoviesFragmentComponenet)
        whenever(builder.fragmentModule(any())).thenReturn(builder)

        val app = InstrumentationRegistry.getTargetContext().applicationContext as EspressoMoviesPreviewApp
        app.putFragmentComponentBuilder(builder, MoviesFragment::class.java)
    }

    @Test
    fun showMoviePage() {
        val dataMoviePage = activityRule.loadDomainPage(1)
        val uiMoviePage = DomainToUiDataMapper().convertDomainMoviePageToUiMoviePage(dataMoviePage, mockPosterImageConfig()[0], mockMovieGenres())

        doAnswer {
            val viewInstance = it.arguments[0] as MoviesView
            viewInstance.showMoviePage(uiMoviePage)
        }.whenever(moviesPresenter).linkView(any())

        launchActivityAndAddFragment()

        // verify all pages where loaded
        onView(withId(R.id.rv_movies))
                .check(RecyclerViewItemCountAssertion(20))
    }


    @Test
    fun appShowsConnectivityError() {
        doAnswer {
            val viewInstance = it.arguments[0] as MoviesView
            viewInstance.showNotConnectedToNetwork()
        }.whenever(moviesPresenter).linkView(any())

        launchActivityAndAddFragment()

        onView(withText(R.string.alert_no_network_connection_message))
                .check(matches(isDisplayed()))
    }


    @Test
    fun appShowsUnexpectedError() {
        doAnswer {
            val viewInstance = it.arguments[0] as MoviesView
            viewInstance.showUnexpectedError()
        }.whenever(moviesPresenter).linkView(any())

        launchActivityAndAddFragment()

        onView(withText(R.string.alert_unexpected_error_message))
                .check(matches(isDisplayed()))
    }


    private fun launchActivityAndAddFragment() {
        activityRule.launch(Intent())
        activityRule.activity.addFragmentIfNotInStack(android.R.id.content, MoviesFragment.newInstance(), MoviesFragment.TAG)
    }


    class EspressoMoviesFragmentComponent(private val moviesPresenterInstance: MoviesPresenter) : MoviesFragmentComponent {

        override fun injectMembers(instance: MoviesFragment?) {
            whenNotNull(instance, {
                it.moviesPresenter = moviesPresenterInstance
            })
        }

    }

}