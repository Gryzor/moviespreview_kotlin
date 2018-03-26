package com.jpp.moviespreview.app.ui.sections.search

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.widget.EditText
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.*
import com.jpp.moviespreview.app.extentions.MoviesPreviewActivityTestRule
import com.jpp.moviespreview.app.extentions.launch
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.sections.search.di.MultiSearchActivityComponent
import com.jpp.moviespreview.app.util.extentions.whenNotNull
import com.jpp.moviespreview.app.utils.RecyclerViewItemCountAssertion
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Rule
import org.junit.Test


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

    private val multiSearchPresenter: MultiSearchPresenter = mock()
    private val builder: MultiSearchActivityComponent.Builder = mock()
    private val mockMultiSearchActivityComponent by lazy {
        EsspresoMultiSearchActivityComponent(multiSearchPresenter)
    }


    @Before
    fun setUp() {
        whenever(builder.build()).thenReturn(mockMultiSearchActivityComponent)
        whenever(builder.activityModule((any()))).thenReturn(builder)

        val app = InstrumentationRegistry.getTargetContext().applicationContext as EspressoMoviesPreviewApp
        app.putActivityComponentBuilder(builder, MultiSearchActivity::class.java)
    }


    @Test
    fun viewClearsLastSearchInPresenterWhenCloseButtonIsClicked() {
        activityRule.launch(Intent())

        onView(withId(R.id.search_view)).perform(click())
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("search"))
        onView(withId(android.support.v7.appcompat.R.id.search_close_btn)).perform(click())

        verify(multiSearchPresenter).clearLastSearch()
    }

    @Test
    fun viewClearsLastSearchInPresenterWhenBackButtonIsPressedInActionBar() {
        activityRule.launch(Intent())

        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());

        verify(multiSearchPresenter).clearLastSearch()
    }


    @Test
    fun showResultsShowsDataInList() {
        val dataSearchPage = activityRule.loadDomainMultiSearchResult(1, "query")
        val uiSearchPage = DomainToUiDataMapper().convertDomainResultPageInUiResultPage(dataSearchPage, mockPosterImageConfig()[0], mockProfileImageConfig()[0], mockMovieGenres())

        doAnswer {
            val viewInstance = it.arguments[0] as MultiSearchView
            viewInstance.showResults(uiSearchPage.results)
        }.whenever(multiSearchPresenter).linkView(any())

        activityRule.launch(Intent())

        // verify all pages where loaded
        onView(withId(R.id.search_results_recycler_view))
                .check(RecyclerViewItemCountAssertion(20))
    }


    @Test
    fun appShowsConnectivityError() {
        doAnswer {
            val viewInstance = it.arguments[0] as MultiSearchView
            viewInstance.showNotConnectedToNetwork()
        }.whenever(multiSearchPresenter).linkView(any())

        activityRule.launch(Intent())

        onView(withText(R.string.alert_no_network_connection_message))
                .check(ViewAssertions.matches(isDisplayed()))
    }


    @Test
    fun appShowsUnexpectedError() {
        doAnswer {
            val viewInstance = it.arguments[0] as MultiSearchView
            viewInstance.showUnexpectedError()
        }.whenever(multiSearchPresenter).linkView(any())

        activityRule.launch(Intent())

        onView(withText(R.string.alert_unexpected_error_message))
                .check(ViewAssertions.matches(isDisplayed()))
    }


    class EsspresoMultiSearchActivityComponent(private val multiSearchPresenterInstance: MultiSearchPresenter) : MultiSearchActivityComponent {
        override fun injectMembers(instance: MultiSearchActivity?) {
            whenNotNull(instance) {
                it.presenter = multiSearchPresenterInstance
            }
        }
    }
}
