package com.jpp.moviespreview.app.ui.sections.detail.credits

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.TestComponentRule
import com.jpp.moviespreview.app.completeConfig
import com.jpp.moviespreview.app.domain.MovieCredits
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.extentions.MoviesPreviewActivityTestRule
import com.jpp.moviespreview.app.extentions.launch
import com.jpp.moviespreview.app.extentions.rotate
import com.jpp.moviespreview.app.mock
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.ApplicationMoviesContext
import com.jpp.moviespreview.app.ui.ProfileImageConfiguration
import com.jpp.moviespreview.app.ui.util.EspressoTestActivity
import com.jpp.moviespreview.app.util.extentions.addFragmentIfNotInStack
import com.jpp.moviespreview.app.utils.RecyclerViewItemCountAssertion
import com.nhaarman.mockito_kotlin.verify
import junit.framework.Assert.assertNotNull
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import javax.inject.Inject
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
    @get:Rule
    val testComponentRule = TestComponentRule()

    @Inject
    lateinit var useCase: UseCase<DomainMovie, MovieCredits>
    @Inject
    lateinit var moviesContext: ApplicationMoviesContext
    @Inject
    lateinit var mapper: DomainToUiDataMapper

    private lateinit var selectedMovie: UiMovie

    @Before
    fun setUp() {
        testComponentRule.testComponent?.inject(this)
        selectedMovie = mock()
        moviesContext.completeConfig()
        createUiMovieFromModel()
    }


    @Test
    fun onActivityCreatedShowsLoadingWhileRetrievingCredits() {
        val domainMovieCredits = activityRule.loadDomainMovieCredits()
        `when`(useCase.execute(any(DomainMovie::class.java))).thenReturn(domainMovieCredits)

        launchActivityAndAddFragment()

        // check all credits are loaded
        onView(ViewMatchers.withId(R.id.rv_movie_credits))
                .check(RecyclerViewItemCountAssertion(129))

        onView(ViewMatchers.withId(R.id.loading_credits_view))
                .check(ViewAssertions.matches(not(isDisplayed())))

        // only one call
        verify(useCase).execute(any(DomainMovie::class.java))

        // saves credits
        assertNotNull(moviesContext.getCreditsForMovie(moviesContext.selectedMovie!!))
    }


    @Test
    fun errorRetrievingMovieCredits() {
        `when`(useCase.execute(any(DomainMovie::class.java))).thenReturn(null)

        launchActivityAndAddFragment()

        onView(ViewMatchers.withId(R.id.movie_credits_error_text_view))
                .check(ViewAssertions.matches(isDisplayed()))

        onView(ViewMatchers.withId(R.id.loading_credits_view))
                .check(ViewAssertions.matches(not(isDisplayed())))
    }

    @Test
    fun orientationChangeDoesNotRequestNewData() {
        val domainMovieCredits = activityRule.loadDomainMovieCredits()
        `when`(useCase.execute(any(DomainMovie::class.java))).thenReturn(domainMovieCredits)

        launchActivityAndAddFragment()

        // sanity check
        onView(ViewMatchers.withId(R.id.rv_movie_credits))
                .check(RecyclerViewItemCountAssertion(129))
        onView(ViewMatchers.withId(R.id.loading_credits_view))
                .check(ViewAssertions.matches(not(isDisplayed())))

        // rotate 1
        activityRule.rotate()
        onView(ViewMatchers.withId(R.id.rv_movie_credits))
                .check(RecyclerViewItemCountAssertion(129))
        onView(ViewMatchers.withId(R.id.loading_credits_view))
                .check(ViewAssertions.matches(not(isDisplayed())))

        // rotate 2
        activityRule.rotate()
        onView(ViewMatchers.withId(R.id.rv_movie_credits))
                .check(RecyclerViewItemCountAssertion(129))
        onView(ViewMatchers.withId(R.id.loading_credits_view))
                .check(ViewAssertions.matches(not(isDisplayed())))

        // only one call
        verify(useCase).execute(any(DomainMovie::class.java))

    }

    /**************************
     **** HELPER FUNCTIONS ****
     **************************/

    private fun createUiMovieFromModel() {

        val domainGenres = activityRule.loadDomainGenres()
        val uiGenres = mapper.convertDomainGenresIntoUiGenres(domainGenres)

        val selectedImageConfiguration = ProfileImageConfiguration("baseUrl", "h200")

        val modelMoviePage = activityRule.loadDomainPage(1)
        val uiMoviePage = mapper.convertDomainMoviePageToUiMoviePage(modelMoviePage, selectedImageConfiguration, uiGenres)
        selectedMovie = uiMoviePage.results[0]
        moviesContext.selectedMovie = selectedMovie

    }

    private fun launchActivityAndAddFragment() {
        activityRule.launch(Intent())
        activityRule.activity.addFragmentIfNotInStack(android.R.id.content, MovieCreditsFragment.newInstance(), "Tag")
    }
}