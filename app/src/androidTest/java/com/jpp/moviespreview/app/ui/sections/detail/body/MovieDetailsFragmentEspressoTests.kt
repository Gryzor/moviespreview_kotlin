package com.jpp.moviespreview.app.ui.sections.detail.body

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.TestComponentRule
import com.jpp.moviespreview.app.extentions.launch
import com.jpp.moviespreview.app.mock
import com.jpp.moviespreview.app.mockMovieGenres
import com.jpp.moviespreview.app.ui.Movie
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.sections.detail.body.MovieDetailsFragment
import com.jpp.moviespreview.app.ui.util.EspressoTestActivity
import com.jpp.moviespreview.app.util.extentions.addFragmentIfNotInStack
import com.jpp.moviespreview.app.utils.RecyclerViewItemCountAssertion
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import javax.inject.Inject

/**
 * Tests the [MovieDetailsFragment] and it's interaction with [PlayingMoviesPresenterImpl]
 * Created by jpp on 12/15/17.
 */
@RunWith(AndroidJUnit4::class)
class MovieDetailsFragmentEspressoTests {

    @get:Rule
    @JvmField
    val activityRule = ActivityTestRule(EspressoTestActivity::class.java)
    @get:Rule
    val testComponentRule = TestComponentRule()

    @Inject
    lateinit var moviesContext: MoviesContext

    private lateinit var selectedMovie: Movie

    @Before
    fun setUp() {
        testComponentRule.testComponent?.inject(this)
        selectedMovie = mock()
        moviesContext.selectedMovie = selectedMovie
    }

    @Test
    fun showsAllMovieDetails() {
        val expectedOverview = "Overview"
        val expectedGenres = mockMovieGenres()
        val expectedVoteCount = 12.toDouble()
        val expectedPopularity = 12.toFloat()

        `when`(selectedMovie.overview).thenReturn(expectedOverview)
        `when`(selectedMovie.genres).thenReturn(expectedGenres)
        `when`(selectedMovie.voteCount).thenReturn(expectedVoteCount)
        `when`(selectedMovie.popularity).thenReturn(expectedPopularity)
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
}