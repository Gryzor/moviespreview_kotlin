package com.jpp.moviespreview.app.ui.sections.main.playing

import com.jpp.moviespreview.app.BackgroundInteractorForTesting
import com.jpp.moviespreview.app.domain.MoviePage
import com.jpp.moviespreview.app.domain.PageParam
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.mock
import com.jpp.moviespreview.app.mockMovieGenres
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.interactors.BackgroundInteractor
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractor
import com.jpp.moviespreview.app.ui.interactors.PresenterInteractorDelegateImpl
import com.nhaarman.mockito_kotlin.argumentCaptor
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import com.jpp.moviespreview.app.domain.Movie as DomainMovie
import com.jpp.moviespreview.app.domain.MoviePage as DomainMoviePage
import com.jpp.moviespreview.app.ui.MoviePage as UiMoviePage

/**
 * Tests [PlayingMoviesPresenterImpl] that are hard to test in Espresso.
 *
 * Created by jpp on 11/1/17.
 */
class PlayingMoviesPresenterImplTest {

    private lateinit var moviesContext: MoviesContext
    private lateinit var interactorDelegate: PlayingMoviesPresenterInteractor
    private lateinit var connectivityInteractor: ConnectivityInteractor
    private lateinit var backgroundInteractor: BackgroundInteractor
    private lateinit var playingMoviesUseCase: UseCase<PageParam, MoviePage>
    private lateinit var mapper: DomainToUiDataMapper
    private lateinit var playingMoviesView: PlayingMoviesView
    private lateinit var subject: PlayingMoviesPresenterImpl


    @Before
    fun doBefore() {
        backgroundInteractor = BackgroundInteractorForTesting()
        connectivityInteractor = mock()
        val basePresenterInteractorDelegate = PresenterInteractorDelegateImpl(backgroundInteractor, connectivityInteractor)
        interactorDelegate = PlayingMoviesPresenterInteractorImpl(basePresenterInteractorDelegate, mock())


        moviesContext = mock()
        playingMoviesUseCase = mock()
        mapper = DomainToUiDataMapper()
        playingMoviesView = mock()

        subject = PlayingMoviesPresenterImpl(moviesContext, interactorDelegate, playingMoviesUseCase, mapper)
    }


    @Test
    fun getNextMoviePageRetrievesNextMoviePage() {
        //--prepare
        subject.linkView(playingMoviesView)

        `when`(moviesContext.isConfigCompleted()).thenReturn(true)
        `when`(moviesContext.movieGenres).thenReturn(mockMovieGenres())

        val lastMoviePage: UiMoviePage = mock()
        val movieList = listOf(
                mock(UiMoviePage::class.java),
                mock(UiMoviePage::class.java),
                mock(UiMoviePage::class.java),
                lastMoviePage
        )
        `when`(moviesContext.getAllMoviePages()).thenReturn(movieList)
        `when`(lastMoviePage.page).thenReturn(4)
        `when`(lastMoviePage.totalPages).thenReturn(29)

        //-- execute
        subject.getNextMoviePage()

        //-- verify
        argumentCaptor<PageParam>().apply {
            verify(playingMoviesUseCase).execute(capture())
            Assert.assertEquals(5, firstValue.page)
            Assert.assertEquals(4, firstValue.genres.size)
        }
    }

    @Test
    fun getNextMoviePageWhenInteractorIsNotIdleDoesNothing() {
        //--prepare
        subject.linkView(playingMoviesView)
        (backgroundInteractor as BackgroundInteractorForTesting).idle = false

        `when`(moviesContext.isConfigCompleted()).thenReturn(true)

        //-- execute
        subject.getNextMoviePage()

        //-- verify
        verifyZeroInteractions(playingMoviesUseCase)
    }


    @Test
    fun createNextUseCaseParamWhenNoMorePagesShowsEndOfPaging() {
        //--prepare
        subject.linkView(playingMoviesView)

        `when`(moviesContext.isConfigCompleted()).thenReturn(true)
        `when`(moviesContext.movieGenres).thenReturn(mockMovieGenres())

        val lastMoviePage: UiMoviePage = mock()
        val movieList = listOf(
                mock(UiMoviePage::class.java),
                mock(UiMoviePage::class.java),
                mock(UiMoviePage::class.java),
                lastMoviePage
        )
        `when`(moviesContext.getAllMoviePages()).thenReturn(movieList)
        `when`(lastMoviePage.page).thenReturn(29)
        `when`(lastMoviePage.totalPages).thenReturn(29)

        //-- execute
        val result = subject.createNextUseCaseParam()

        //-- verify
        verify(playingMoviesView).showEndOfPaging()
        Assert.assertNull(result)
    }
}