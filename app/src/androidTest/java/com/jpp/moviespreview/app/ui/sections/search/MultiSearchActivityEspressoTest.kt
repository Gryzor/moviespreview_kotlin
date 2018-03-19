package com.jpp.moviespreview.app.ui.sections.search

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.Intents.*
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.v7.widget.RecyclerView
import android.widget.AutoCompleteTextView
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.TestComponentRule
import com.jpp.moviespreview.app.completeConfig
import com.jpp.moviespreview.app.domain.MultiSearchPage
import com.jpp.moviespreview.app.domain.MultiSearchParam
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.extentions.MoviesPreviewActivityTestRule
import com.jpp.moviespreview.app.extentions.launch
import com.jpp.moviespreview.app.extentions.rotate
import com.jpp.moviespreview.app.ui.ApplicationMoviesContext
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractor
import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailActivity
import com.jpp.moviespreview.app.utils.RecyclerViewItemCountAssertion
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer
import javax.inject.Inject


/**
 *
 * Espresso tests are to verify the UI module.
 *
 * This test exercises the View, the Presenter and the Mapper for the
 * multi searchFirstPage section.
 *
 * Created by jpp on 1/15/18.
 */

class MultiSearchActivityEspressoTest {

    @get:Rule
    @JvmField
    val activityRule = MoviesPreviewActivityTestRule(MultiSearchActivity::class.java)
    @get:Rule
    val testComponentRule = TestComponentRule()

    @Inject
    lateinit var moviesContext: ApplicationMoviesContext
    @Inject
    lateinit var useCase: UseCase<MultiSearchParam, MultiSearchPage>
    @Inject
    lateinit var connectivityInteractor: ConnectivityInteractor

    @Before
    fun setUp() {
        testComponentRule.testComponent?.inject(this)
    }


    @Test
    fun searchExecutesUseCase() {
        moviesContext.completeConfig()

        val expectedQuery = "ju"
        val initialPage = 1
        val expectedSearchPage = activityRule.loadDomainMultiSearchResult(initialPage, expectedQuery)
        `when`(useCase.execute(Mockito.any(MultiSearchParam::class.java)))
                .thenAnswer(SearchPageAnswer(expectedSearchPage, expectedQuery, initialPage))

        activityRule.launch(Intent())

        onView(isAssignableFrom(AutoCompleteTextView::class.java)).perform(typeText(expectedQuery), pressImeActionButton())


        onView(withId(R.id.search_results_recycler_view))
                .check(RecyclerViewItemCountAssertion(20))
    }

    @Test
    fun orientationChangeDoesNotRequestNewData() {
        // complete context configuration
        moviesContext.completeConfig()

        val expectedQuery = "ju"
        val initialPage = 1
        val expectedSearchPage = activityRule.loadDomainMultiSearchResult(initialPage, expectedQuery)
        `when`(useCase.execute(any(MultiSearchParam::class.java)))
                .thenAnswer(SearchPageAnswer(expectedSearchPage, expectedQuery, initialPage))

        activityRule.launch(Intent())

        onView(isAssignableFrom(AutoCompleteTextView::class.java)).perform(typeText(expectedQuery), pressImeActionButton())

        // sanity check
        onView(withId(R.id.search_results_recycler_view))
                .check(RecyclerViewItemCountAssertion(20))

        // rotate 1
        activityRule.rotate()
        onView(withId(R.id.search_results_recycler_view))
                .check(RecyclerViewItemCountAssertion(20))

        // rotate 3
        activityRule.rotate()
        onView(withId(R.id.search_results_recycler_view))
                .check(RecyclerViewItemCountAssertion(20))

        verify(useCase).execute(any(MultiSearchParam::class.java))
    }


    @Test
    fun appShowsConnectivityErrorWhenRetrievingDataAndNoConnection() {
        // complete context configuration
        moviesContext.completeConfig()

        // return null page will cause error verification
        `when`(useCase.execute(any(MultiSearchParam::class.java))).thenReturn(null)

        // mock connectivity issues
        `when`(connectivityInteractor.isConnectedToNetwork()).thenReturn(false)

        activityRule.launch(Intent())

        onView(isAssignableFrom(AutoCompleteTextView::class.java)).perform(typeText("query"), pressImeActionButton())

        onView(ViewMatchers.withText(R.string.alert_no_network_connection_message))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    @Test
    fun appShowsUnexpectedError() {
        // complete context configuration
        moviesContext.completeConfig()

        // return null page will cause error verification
        `when`(useCase.execute(any(MultiSearchParam::class.java))).thenReturn(null)

        // mock connectivity NO issues
        `when`(connectivityInteractor.isConnectedToNetwork()).thenReturn(true)

        activityRule.launch(Intent())

        onView(isAssignableFrom(AutoCompleteTextView::class.java)).perform(typeText("query"), pressImeActionButton())

        onView(ViewMatchers.withText(R.string.alert_unexpected_error_message))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


    @Test
    fun clearSearch() {
        moviesContext.completeConfig()

        val expectedQuery = "ju"
        val initialPage = 1
        val expectedSearchPage = activityRule.loadDomainMultiSearchResult(initialPage, expectedQuery)
        `when`(useCase.execute(Mockito.any(MultiSearchParam::class.java)))
                .thenAnswer(SearchPageAnswer(expectedSearchPage, expectedQuery, initialPage))

        activityRule.launch(Intent())

        onView(isAssignableFrom(AutoCompleteTextView::class.java)).perform(typeText(expectedQuery), pressImeActionButton())


        //sanity check
        onView(withId(R.id.search_results_recycler_view))
                .check(RecyclerViewItemCountAssertion(20))

        onView(withId(android.support.v7.appcompat.R.id.search_close_btn))
                .perform(click())

        onView(withId(R.id.search_results_recycler_view))
                .check(RecyclerViewItemCountAssertion(0))
    }

    @Test
    fun userSelectsMovieSetsMovieInContextStartDetailsActivity() {
        init()

        // complete context configuration
        moviesContext.completeConfig()


        val expectedQuery = "ju"
        val initialPage = 1
        val expectedSearchPage = activityRule.loadDomainMultiSearchResult(initialPage, expectedQuery)
        `when`(useCase.execute(any(MultiSearchParam::class.java)))
                .thenAnswer(SearchPageAnswer(expectedSearchPage, expectedQuery, initialPage))

        activityRule.launch(Intent())

        onView(isAssignableFrom(AutoCompleteTextView::class.java)).perform(typeText(expectedQuery), pressImeActionButton())

        onView(withId(R.id.search_results_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))

        assertNotNull(moviesContext.selectedMovie)

        intended(IntentMatchers.hasComponent(MovieDetailActivity::class.java.name))
        release()
    }


    private class SearchPageAnswer(private val expectedMoviePage: MultiSearchPage,
                                   private val expectedQuery: String,
                                   private val initialPage: Int? = null) : Answer<MultiSearchPage> {
        override fun answer(invocation: InvocationOnMock?): MultiSearchPage? {
            val argument = invocation!!.arguments[0]
            return if (argument is MultiSearchParam) {
                val page = argument.page
                if (initialPage != null) {
                    assertEquals(initialPage, page)
                }
                val query = argument.query
                assertEquals(expectedQuery, query)
                return expectedMoviePage
            } else {
                null
            }
        }
    }

}
