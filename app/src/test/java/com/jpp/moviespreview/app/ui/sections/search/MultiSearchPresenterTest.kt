package com.jpp.moviespreview.app.ui.sections.search

import com.jpp.moviespreview.app.BackgroundExecutorForTesting
import com.jpp.moviespreview.app.mock
import com.jpp.moviespreview.app.ui.*
import com.jpp.moviespreview.app.ui.interactors.ImageConfigurationManager
import com.jpp.moviespreview.app.util.StubPaginationController
import com.nhaarman.mockito_kotlin.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Created by jpp on 3/24/18.
 */
class MultiSearchPresenterTest {

    private lateinit var multiSearchView: MultiSearchView
    private lateinit var multiSearchContext: MultiSearchContext
    private lateinit var querySubmitManager: QuerySubmitManager
    private lateinit var backgroundExecutor: BackgroundExecutorForTesting
    private lateinit var imageConfigManager: ImageConfigurationManager
    private lateinit var paginationController: StubPaginationController
    private lateinit var searchFlowResolver: MultiSearchFlowResolver
    private lateinit var interactor: MultiSearchInteractor
    private lateinit var subject: MultiSearchPresenter


    @Before
    fun setUp() {
        multiSearchView = mock()
        multiSearchContext = mock()
        querySubmitManager = mock()
        imageConfigManager = mock()
        searchFlowResolver = mock()
        interactor = mock()

        backgroundExecutor = BackgroundExecutorForTesting()
        paginationController = StubPaginationController()

        subject = MultiSearchPresenterImpl(multiSearchContext,
                querySubmitManager,
                backgroundExecutor,
                imageConfigManager,
                paginationController,
                searchFlowResolver,
                interactor)
    }


    /**
     * Good example on how to test lambdas.
     */
    @Test
    fun linkViewStartsListeningQueryUpdates() {
        val queryTextView: QueryTextView = mock()
        val lambdaCaptor = argumentCaptor<(String) -> Unit>()
        val queryTextViewCaptor = argumentCaptor<QueryTextView>()

        whenever(multiSearchView.getQueryTextView()).thenReturn(queryTextView)

        subject.linkView(multiSearchView)

        verify(querySubmitManager).linkQueryTextView(queryTextViewCaptor.capture(), lambdaCaptor.capture())

        assertEquals(queryTextView, queryTextViewCaptor.firstValue)
        /*
         * All we can do here is verify that some value has been captured, meaning
         * that a lambda has been assigned.
         */
        assertNotNull(lambdaCaptor.firstValue)
    }


    @Test
    fun linkViewWhenContextHasPagesShowsThosePages() {
        val searchPages: List<MultiSearchResult> = listOf(mock(), mock())

        whenever(multiSearchContext.hasSearchPages()).thenReturn(true)
        whenever(multiSearchContext.getAllSearchResults()).thenReturn(searchPages)

        subject.linkView(multiSearchView)

        verify(multiSearchView).showResults(searchPages)
    }


    @Test
    fun getNextSearchPageConfigureInteractorAndExecuteSearch() {
        // prepare pagination
        val expectedPage = 2
        paginationController.nextPage = expectedPage
        paginationController.whatToDo = StubPaginationController.CALL_NEXT_PAGE

        // prepare context
        val expectedQuery = "aQuery"
        val lastSearchPage: MultiSearchPage = mock()
        val searchPages: List<MultiSearchPage> = listOf(lastSearchPage)
        whenever(lastSearchPage.query).thenReturn(expectedQuery)
        whenever(multiSearchContext.getAllPages()).thenReturn(searchPages)

        prepareInteractorConfig()

        subject.linkView(multiSearchView)

        subject.getNextSearchPage()

        verify(multiSearchView, times(2)).getTargetMultiSearchResultImageSize()
        verify(interactor).configure(any(), any(), any(), any())
        verify(interactor).searchPage(expectedQuery, expectedPage)
        assertTrue(backgroundExecutor.backgroundExecuted)
    }


    @Test
    fun getNextSearchPageWhenNoMorePagesShowsEndOnPaging() {
        paginationController.whatToDo = StubPaginationController.CALL_END_OF_PAGING

        subject.linkView(multiSearchView)
        subject.getNextSearchPage()

        verify(multiSearchView).showEndOfPaging()
    }

    @Test
    fun clearLastSearchClearsContextAndView() {
        subject.linkView(multiSearchView)
        subject.clearLastSearch()
        verify(multiSearchContext).clearPages()
        verify(multiSearchView).clearPages()
        verify(multiSearchView).clearSearch()
    }

    @Test
    fun onItemSelectedShowsDetails() {
        val selectedItem: MultiSearchResult = mock()
        val movieDetails: Movie = mock()

        whenever(selectedItem.movieDetails).thenReturn(movieDetails)

        subject.onItemSelected(selectedItem)

        verify(multiSearchContext).setSelectedMovie(movieDetails)
        verify(searchFlowResolver).showMovieDetails()
    }


    @Test
    fun executesSearchWhenNewSearchRequestedByTheUser() {
        prepareInteractorConfig()

        val expectedQuery = "aQuery"

        val queryTextView: QueryTextView = mock()
        whenever(multiSearchView.getQueryTextView()).thenReturn(queryTextView)

        doAnswer {
            val action = it.arguments[1] as (String) -> Unit
            action(expectedQuery)
        }.whenever(querySubmitManager).linkQueryTextView(any(), any())

        subject.linkView(multiSearchView)

        verify(multiSearchContext).clearPages()
        verify(multiSearchView).clearPages()
        verify(interactor).searchFirstPage(expectedQuery)
        assertTrue(backgroundExecutor.backgroundExecuted)
    }

    @Test
    fun presenterShowsResultsWhenInteractorReturnsSearch() {
        prepareInteractorConfig()

        val lastSearchPage: MultiSearchPage = mock()
        val allResults: List<MultiSearchResult> = mock()

        val queryTextView: QueryTextView = mock()
        whenever(multiSearchView.getQueryTextView()).thenReturn(queryTextView)

        whenever(multiSearchContext.getAllSearchResults()).thenReturn(allResults)

        doAnswer {
            val searchData = it.arguments[0] as MultiSearchData
            searchData.lastSearchPage = lastSearchPage
        }.whenever(interactor).configure(any(), any(), any(), any())


        doAnswer {
            val action = it.arguments[1] as (String) -> Unit
            action("aQuery")
        }.whenever(querySubmitManager).linkQueryTextView(any(), any())

        subject.linkView(multiSearchView)

        verify(multiSearchContext).addSearchPage(lastSearchPage)
        verify(multiSearchView).showResults(allResults)
    }



    @Test
    fun presenterShowsNoConnectionErrorWhenSearchFails() {
        prepareInteractorConfig()

        val queryTextView: QueryTextView = mock()
        whenever(multiSearchView.getQueryTextView()).thenReturn(queryTextView)

        doAnswer {
            val searchData = it.arguments[0] as MultiSearchData
            searchData.error = Error(Error.NO_CONNECTION)
        }.whenever(interactor).configure(any(), any(), any(), any())

        doAnswer {
            val action = it.arguments[1] as (String) -> Unit
            action("aQuery")
        }.whenever(querySubmitManager).linkQueryTextView(any(), any())

        subject.linkView(multiSearchView)

        verify(multiSearchView).showNotConnectedToNetwork()
    }

    @Test
    fun presenterShowsUnknownErrorWhenSearchFails() {
        prepareInteractorConfig()

        val queryTextView: QueryTextView = mock()
        whenever(multiSearchView.getQueryTextView()).thenReturn(queryTextView)

        doAnswer {
            val searchData = it.arguments[0] as MultiSearchData
            searchData.error = Error(Error.UNKNOWN)
        }.whenever(interactor).configure(any(), any(), any(), any())

        doAnswer {
            val action = it.arguments[1] as (String) -> Unit
            action("aQuery")
        }.whenever(querySubmitManager).linkQueryTextView(any(), any())

        subject.linkView(multiSearchView)

        verify(multiSearchView).showUnexpectedError()
    }


    private fun prepareInteractorConfig() {
        whenever(multiSearchContext.getUIMovieGenres()).thenReturn(mock())

        val selectedPosterImageConfiguration: PosterImageConfiguration = mock()
        whenever(multiSearchContext.getPosterImageConfigs()).thenReturn(mock())
        whenever(imageConfigManager.findPosterImageConfigurationForWidth(any(), any())).thenReturn(selectedPosterImageConfiguration)

        val selectedProfileImageConfiguration: ProfileImageConfiguration = mock()
        whenever(multiSearchContext.getProfileImageConfig()).thenReturn(mock())
        whenever(imageConfigManager.findProfileImageConfigurationForHeight(any(), any())).thenReturn(selectedProfileImageConfiguration)
    }
}