package com.jpp.moviespreview.app.ui.sections.detail.body

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.runner.AndroidJUnit4
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.EspressoMoviesPreviewApp
import com.jpp.moviespreview.app.extentions.MoviesPreviewActivityTestRule
import com.jpp.moviespreview.app.extentions.launch
import com.jpp.moviespreview.app.mock
import com.jpp.moviespreview.app.mockMovieGenres
import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailPresenter
import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailView
import com.jpp.moviespreview.app.ui.sections.detail.body.di.MovieDetailsFragmentComponent
import com.jpp.moviespreview.app.ui.util.EspressoTestActivity
import com.jpp.moviespreview.app.util.extentions.addFragmentIfNotInStack
import com.jpp.moviespreview.app.util.extentions.whenNotNull
import com.jpp.moviespreview.app.utils.RecyclerViewItemCountAssertion
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`

/**
 * Tests the [MovieDetailsFragment] and it's interaction with [PlayingMoviesPresenterImpl]
 * Created by jpp on 12/15/17.
 */
@RunWith(AndroidJUnit4::class)
class MovieDetailsFragmentEspressoTests {

    @get:Rule
    @JvmField
    val activityRule = MoviesPreviewActivityTestRule(EspressoTestActivity::class.java)


    private val presenter: MovieDetailPresenter = mock()
    private val builder: MovieDetailsFragmentComponent.Builder = mock()
    private val mockCreditsFragmentComponent by lazy {
        EspressoMovieDetailsFragmentComponent(presenter)
    }

    @Before
    fun setUp() {
        `when`(builder.build()).thenReturn(mockCreditsFragmentComponent)
        `when`(builder.fragmentModule(any())).thenReturn(builder)

        val app = InstrumentationRegistry.getTargetContext().applicationContext as EspressoMoviesPreviewApp
        app.putFragmentComponentBuilder(builder, MovieDetailsFragment::class.java)
    }

    @Test
    fun showsAllMovieDetails() {
        val expectedOverview = "Overview"
        val expectedGenres = mockMovieGenres()
        val expectedVoteCount = 12.toDouble()
        val expectedPopularity = 12.toFloat()


        doAnswer {
            val viewInstance = it.arguments[0] as MovieDetailView
            viewInstance.showMovieGenres(expectedGenres)
            viewInstance.showMovieOverview(expectedOverview)
            viewInstance.showMoviePopularity(expectedPopularity)
            viewInstance.showMovieVoteCount(expectedVoteCount)
        }.`when`(presenter).linkView(any())

        launchActivityAndAddFragment()

        onView(withId(R.id.movie_details_fragment_overview_body))
                .check(matches(withText(expectedOverview)))

        onView(withId(R.id.movie_details_fragment_recycler_view))
                .check(RecyclerViewItemCountAssertion(expectedGenres.size))

        onView(withId(R.id.movie_details_fragment_vote_count_text_view))
                .check(matches(withText(expectedVoteCount.toString())))

        onView(withId(R.id.movie_details_popularity_text_view))
                .check(matches(withText(expectedPopularity.toString())))
    }


    private fun launchActivityAndAddFragment() {
        activityRule.launch(Intent())
        activityRule.activity.addFragmentIfNotInStack(android.R.id.content, MovieDetailsFragment.newInstance(), "Tag")
    }


    class EspressoMovieDetailsFragmentComponent(private val movieDetailPresenter: MovieDetailPresenter) : MovieDetailsFragmentComponent {
        override fun injectMembers(instance: MovieDetailsFragment?) {
            whenNotNull(instance, {
                it.presenter = movieDetailPresenter
            })
        }
    }
}