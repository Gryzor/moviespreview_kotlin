package com.jpp.moviespreview.app.ui.sections.main.movies

import com.jpp.moviespreview.app.BackgroundExecutorForTesting
import com.jpp.moviespreview.app.mock
import com.jpp.moviespreview.app.ui.Error
import com.jpp.moviespreview.app.ui.MoviePage
import com.jpp.moviespreview.app.ui.MoviesContextHandler
import com.jpp.moviespreview.app.ui.interactors.BackgroundExecutor
import com.jpp.moviespreview.app.ui.interactors.ImageConfigurationManager
import com.jpp.moviespreview.app.util.StubPaginationController
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times

/**
 * Created by jpp on 2/21/18.
 */
class MoviesPresenterTest {

    private lateinit var moviesContextHandler: MoviesContextHandler
    private lateinit var backgroundExecutor: BackgroundExecutor
    private lateinit var moviesPresenterInteractor: MoviesPresenterInteractor
    private lateinit var paginationController: StubPaginationController
    private lateinit var imageConfigManager: ImageConfigurationManager
    private lateinit var moviesView: MoviesView

    private lateinit var subject: MoviesPresenter

    @Before
    fun setUp() {
        moviesContextHandler = mock()
        moviesPresenterInteractor = mock()
        imageConfigManager = mock()
        moviesView = mock()

        paginationController = StubPaginationController()
        backgroundExecutor = BackgroundExecutorForTesting()


        subject = MoviesPresenterImpl(moviesContextHandler, backgroundExecutor, moviesPresenterInteractor, paginationController, imageConfigManager)
    }


    @Test
    fun linkViewWhenContextNotCompletedGoesBackToSplashScreen() {
        `when`(moviesContextHandler.isConfigCompleted()).thenReturn(false)

        subject.linkView(moviesView)

        verify(moviesView).backToSplashScreen()
    }

    @Test // tests rotation of the view
    fun linkViewWhenMoviesInContextShowsThoseMovies() {
        val moviesInContext: List<MoviePage> = listOf(mock(), mock(), mock(), mock())

        `when`(moviesContextHandler.isConfigCompleted()).thenReturn(true)
        `when`(moviesContextHandler.getAllMoviePages()).thenReturn(moviesInContext)

        subject.linkView(moviesView)

        verify(moviesView, times(4)).showMoviePage(any())
    }

    @Test
    fun linkViewWhenNoMoviesInContextConfiguresInteractorAndRetrievesMovies() {
        val moviesInContext: List<MoviePage> = listOf()
        val expectedPage = 1

        `when`(moviesContextHandler.isConfigCompleted()).thenReturn(true)
        `when`(moviesContextHandler.getAllMoviePages()).thenReturn(moviesInContext)
        `when`(moviesContextHandler.getMovieGenres()).thenReturn(mock())
        `when`(moviesContextHandler.getPosterImageConfigs()).thenReturn(mock())
        `when`(imageConfigManager.findPosterImageConfigurationForWidth(any(), any())).thenReturn(mock())

        paginationController.nextPage = expectedPage
        paginationController.whatToDo = StubPaginationController.CALL_NEXT_PAGE

        subject.linkView(moviesView)

        verify(moviesPresenterInteractor).configure(any(), any(), any())
        verify(moviesPresenterInteractor).retrieveMoviePage(expectedPage)
    }


    @Test
    fun getNextPageWhenContextNotCompletedGoesBackToSplashScreen() {
        `when`(moviesContextHandler.isConfigCompleted()).thenReturn(false)

        subject.linkView(moviesView)
        subject.getNextMoviePage()

        // 2 times = one for link, one for getNextMoviePage
        verify(moviesView, times(2)).backToSplashScreen()
    }

    @Test
    fun getNextMoviePageShowsLoadingAndRetrievesFirstPage() {
        val expectedPage = 1

        paginationController.nextPage = expectedPage
        paginationController.whatToDo = StubPaginationController.CALL_NEXT_PAGE

        //prepare
        linkView()

        //execute
        subject.getNextMoviePage()

        // 2 times = one for link, one for getNextMoviePage
        verify(moviesPresenterInteractor, times(2)).retrieveMoviePage(expectedPage)
        verify(moviesView,  times(2)).showLoading()
    }


    @Test
    fun getNextMoviePageWhenEndOfPaging() {
        val moviesInContext: List<MoviePage> = listOf(mock(), mock(), mock(), mock())
        val expectedPage = 1

        `when`(moviesContextHandler.isConfigCompleted()).thenReturn(true)
        `when`(moviesContextHandler.getAllMoviePages()).thenReturn(moviesInContext)
        `when`(moviesContextHandler.getMovieGenres()).thenReturn(mock())
        `when`(moviesContextHandler.getPosterImageConfigs()).thenReturn(mock())
        `when`(imageConfigManager.findPosterImageConfigurationForWidth(any(), any())).thenReturn(mock())
        `when`(moviesView.isShowingMovies()).thenReturn(false)

        paginationController.whatToDo = StubPaginationController.CALL_END_OF_PAGING

        //prepare
        linkView()

        //execute
        subject.getNextMoviePage()

        // 2 times = one for link, one for getNextMoviePage
        verify(moviesPresenterInteractor, times(0)).retrieveMoviePage(expectedPage)
        verify(moviesView,  times(2)).showEndOfPaging()
    }


    @Test
    fun getNextMoviePageSetsNewPageInContextAndShowsIt() {
        val expectedMoviePage: MoviePage = mock()
        var movieData: MoviesData? = null

        doAnswer {
            movieData = it.arguments[0] as MoviesData
        }.`when`(moviesPresenterInteractor).configure(any(), any(), any())

        doAnswer {

            movieData?.let {
                it.lastMoviePage = expectedMoviePage
            } ?: run {
                fail()
            }

        }.`when`(moviesPresenterInteractor).retrieveMoviePage(any())


        linkView()

        subject.getNextMoviePage()

        // 2 times = one for link, one for getNextMoviePage
        verify(moviesContextHandler, times(2)).addMoviePage(expectedMoviePage)
        verify(moviesView, times(2)).showMoviePage(expectedMoviePage)
    }


    @Test
    fun getNextMoviePageFailsWithNoInternetMessage() {
        val expectedMoviePage: MoviePage = mock()
        var movieData: MoviesData? = null

        doAnswer {
            movieData = it.arguments[0] as MoviesData
        }.`when`(moviesPresenterInteractor).configure(any(), any(), any())

        doAnswer {

            movieData?.let {
                it.error = Error(Error.NO_CONNECTION)
            } ?: run {
                fail()
            }

        }.`when`(moviesPresenterInteractor).retrieveMoviePage(any())


        linkView()

        subject.getNextMoviePage()

        // 1 time1  for link view
        verify(moviesContextHandler, never()).addMoviePage(expectedMoviePage)
        verify(moviesView, never()).showMoviePage(expectedMoviePage)
        verify(moviesView, times(2)).showNotConnectedToNetwork()
    }


    @Test
    fun getNextMoviePageFailsWithUnknownMessage() {
        val expectedMoviePage: MoviePage = mock()
        var movieData: MoviesData? = null

        doAnswer {
            movieData = it.arguments[0] as MoviesData
        }.`when`(moviesPresenterInteractor).configure(any(), any(), any())

        doAnswer {

            movieData?.let {
                it.error = Error(Error.UNKNOWN)
            } ?: run {
                fail()
            }

        }.`when`(moviesPresenterInteractor).retrieveMoviePage(any())


        linkView()

        subject.getNextMoviePage()

        // 1 time1  for link view
        verify(moviesContextHandler, never()).addMoviePage(expectedMoviePage)
        verify(moviesView, never()).showMoviePage(expectedMoviePage)
        verify(moviesView, times(2)).showUnexpectedError()
    }


    private fun linkView() {
        val moviesInContext: List<MoviePage> = listOf()

        `when`(moviesContextHandler.isConfigCompleted()).thenReturn(true)
        `when`(moviesContextHandler.getAllMoviePages()).thenReturn(moviesInContext)
        `when`(moviesContextHandler.getMovieGenres()).thenReturn(mock())
        `when`(moviesContextHandler.getPosterImageConfigs()).thenReturn(mock())
        `when`(imageConfigManager.findPosterImageConfigurationForWidth(any(), any())).thenReturn(mock())
        `when`(moviesView.isShowingMovies()).thenReturn(false)

        subject.linkView(moviesView)
    }

}