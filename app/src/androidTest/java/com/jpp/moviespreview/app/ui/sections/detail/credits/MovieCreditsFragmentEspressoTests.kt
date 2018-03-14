package com.jpp.moviespreview.app.ui.sections.detail.credits

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.EspressoMoviesPreviewApp
import com.jpp.moviespreview.app.extentions.MoviesPreviewActivityTestRule
import com.jpp.moviespreview.app.extentions.launch
import com.jpp.moviespreview.app.mock
import com.jpp.moviespreview.app.mockProfileImageConfig
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailCreditsPresenter
import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailCreditsView
import com.jpp.moviespreview.app.ui.sections.detail.credits.di.MovieCreditsFragmentComponent
import com.jpp.moviespreview.app.ui.util.EspressoTestActivity
import com.jpp.moviespreview.app.util.extentions.addFragmentIfNotInStack
import com.jpp.moviespreview.app.util.extentions.whenNotNull
import com.jpp.moviespreview.app.utils.RecyclerViewItemCountAssertion
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import com.jpp.moviespreview.app.domain.Movie as DomainMovie
import com.jpp.moviespreview.app.domain.MovieCredits as DomainMovieCredits
import com.jpp.moviespreview.app.ui.Movie as UiMovie
import com.jpp.moviespreview.app.ui.MovieGenre as UiMovieGenre

/**
 * Tests the [MovieCreditsFragment] and its interaction with [MovieDetailCreditsPresenterImpl]
 *
 * Created by jpp on 1/2/18.
 */
class MovieCreditsFragmentEspressoTests {

    @get:Rule
    @JvmField
    val activityRule = MoviesPreviewActivityTestRule(EspressoTestActivity::class.java)

    private val presenter: MovieDetailCreditsPresenter = mock()
    private val builder: MovieCreditsFragmentComponent.Builder = mock()
    private val mockCreditsFragmentComponent by lazy {
        EspressoMovieCreditsFragmentComponent(presenter)
    }


    @Before
    fun setUp() {
        `when`(builder.build()).thenReturn(mockCreditsFragmentComponent)
        `when`(builder.fragmentModule(com.nhaarman.mockito_kotlin.any())).thenReturn(builder)

        val app = InstrumentationRegistry.getTargetContext().applicationContext as EspressoMoviesPreviewApp
        app.putFragmentComponentBuilder(builder, MovieCreditsFragment::class.java)
    }


    @Test
    fun showLoading() {
        doAnswer {
            val viewInstance = it.arguments[0] as MovieDetailCreditsView
            viewInstance.showLoading()
        }.`when`(presenter).linkView(any())

        launchActivityAndAddFragment()

        onView(withId(R.id.loading_credits_view))
                .check(matches(isDisplayed()))
    }


    @Test
    fun showMovieCredits() {
        val domainMovieCredits = activityRule.loadDomainMovieCredits()
        val selectedImageConfiguration = mockProfileImageConfig()[0]
        val uiCredits = DomainToUiDataMapper().convertDomainCreditsInUiCredits(domainMovieCredits.cast, domainMovieCredits.crew, selectedImageConfiguration)

        doAnswer {
            val viewInstance = it.arguments[0] as MovieDetailCreditsView
            viewInstance.showMovieCredits(uiCredits)
        }.`when`(presenter).linkView(any())

        launchActivityAndAddFragment()

        // check all credits are loaded
        onView(withId(R.id.rv_movie_credits))
                .check(RecyclerViewItemCountAssertion(129))

        onView(withId(R.id.loading_credits_view))
                .check(matches(not(isDisplayed())))
    }


    @Test
    fun showErrorRetrievingCredits() {
        doAnswer {
            val viewInstance = it.arguments[0] as MovieDetailCreditsView
            viewInstance.showErrorRetrievingCredits()
        }.`when`(presenter).linkView(any())

        launchActivityAndAddFragment()

        onView(withId(R.id.movie_credits_error_text_view))
                .check(matches(isDisplayed()))

        onView(withId(R.id.loading_credits_view))
                .check(matches(not(isDisplayed())))
    }


    private fun launchActivityAndAddFragment() {
        activityRule.launch(Intent())
        activityRule.activity.addFragmentIfNotInStack(android.R.id.content, MovieCreditsFragment.newInstance(), "Tag")
    }


    class EspressoMovieCreditsFragmentComponent(private val movieDetailCreditsPresenter: MovieDetailCreditsPresenter) : MovieCreditsFragmentComponent {
        override fun injectMembers(instance: MovieCreditsFragment?) {
            whenNotNull(instance, {
                it.presenter = movieDetailCreditsPresenter
            })
        }

    }
}