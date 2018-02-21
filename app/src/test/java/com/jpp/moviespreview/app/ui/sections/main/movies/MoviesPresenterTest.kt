package com.jpp.moviespreview.app.ui.sections.main.movies

import com.jpp.moviespreview.app.BackgroundExecutorForTesting
import com.jpp.moviespreview.app.mock
import com.jpp.moviespreview.app.ui.MoviePage
import com.jpp.moviespreview.app.ui.PosterImageConfiguration
import com.jpp.moviespreview.app.ui.interactors.BackgroundExecutor
import com.jpp.moviespreview.app.ui.interactors.ImageConfigurationManager
import com.jpp.moviespreview.app.ui.interactors.PaginationController
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
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
    private lateinit var paginationController: PaginationController
    private lateinit var imageConfigManager: ImageConfigurationManager
    private lateinit var moviesView: MoviesView

    private lateinit var subject: MoviesPresenter

    @Before
    fun setUp() {
        moviesContextHandler = mock()
        moviesPresenterInteractor = mock()
        paginationController = mock()
        imageConfigManager = mock()
        backgroundExecutor = BackgroundExecutorForTesting()
        moviesView = mock()

        subject = MoviesPresenterImpl(moviesContextHandler, backgroundExecutor, moviesPresenterInteractor, paginationController, imageConfigManager)
    }


    @Test
    fun linkViewWhenContextNotCompletedGoesBackToSplashWhenContextNotCompleted() {
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
        // Creo que no hacen falta
//        val posterImageConfigs: List<PosterImageConfiguration> = mock()
//        val screenWidth = 128
//        val selectedPosterImageConfiguration: PosterImageConfiguration = mock()
//
//
//        `when`(moviesContextHandler.getPosterImageConfigs()).thenReturn(posterImageConfigs)
//        `when`(moviesView.getScreenWidth()).thenReturn(screenWidth)
//        `when`(imageConfigManager.findPosterImageConfigurationForWidth(posterImageConfigs, screenWidth)).thenReturn(selectedPosterImageConfiguration)

        `when`(moviesContextHandler.isConfigCompleted()).thenReturn(true)



        verify(moviesPresenterInteractor).configure(any(), any(), any())

    }

}