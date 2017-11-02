package com.jpp.moviespreview.app.ui.main.playing

import com.jpp.moviespreview.app.BackgroundInteractorForTesting
import com.jpp.moviespreview.app.domain.Genre
import com.jpp.moviespreview.app.domain.MoviePage
import com.jpp.moviespreview.app.domain.MoviesInTheaterInputParam
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.mock
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.ImageConfiguration
import com.jpp.moviespreview.app.ui.MovieGenre
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.interactors.BackgroundInteractor
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractor
import com.nhaarman.mockito_kotlin.argumentCaptor
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import com.jpp.moviespreview.app.domain.Movie as DomainMovie
import com.jpp.moviespreview.app.domain.MoviePage as DomainMoviePage
import com.jpp.moviespreview.app.ui.MoviePage as UiMoviePage

/**
 * Tests presenter + DomainToUiDataMapper
 *
 * Created by jpp on 11/1/17.
 */
class PlayingMoviesPresenterImplTest {

    private lateinit var moviesContext: MoviesContext
    private lateinit var interactorDelegate: PlayingMoviesInteractorDelegate
    private lateinit var connectivityInteractor: ConnectivityInteractor
    private lateinit var backgroundInteractor: BackgroundInteractor
    private lateinit var playingMoviesUseCase: UseCase<MoviesInTheaterInputParam, MoviePage>
    private lateinit var mapper: DomainToUiDataMapper
    private lateinit var playingMoviesView: PlayingMoviesView
    private lateinit var subject: PlayingMoviesPresenterImpl


    @Before
    fun doBefore() {
        backgroundInteractor = BackgroundInteractorForTesting()
        connectivityInteractor = mock()
        interactorDelegate = PlayingMoviesInteractorDelegateImpl(backgroundInteractor, connectivityInteractor)


        moviesContext = mock()
        playingMoviesUseCase = mock()
        mapper = DomainToUiDataMapper()
        playingMoviesView = mock()

        subject = PlayingMoviesPresenterImpl(moviesContext, interactorDelegate, playingMoviesUseCase, mapper)
    }

    @Test
    fun linkView_whenMoviePagesInContext_showsDataInContext() {
        //--prepare
        `when`(moviesContext.hasMoviePages()).thenReturn(true)
        val movieList = listOf(
                mock(UiMoviePage::class.java),
                mock(UiMoviePage::class.java),
                mock(UiMoviePage::class.java)
        )
        `when`(moviesContext.getAllMoviePages()).thenReturn(movieList)

        //-- execute
        subject.linkView(playingMoviesView)

        //-- verify
        verify(playingMoviesView).showMoviePage(movieList[0])
        verify(playingMoviesView).showMoviePage(movieList[1])
        verify(playingMoviesView).showMoviePage(movieList[2])
        verifyZeroInteractions(playingMoviesUseCase)
    }

    @Test
    fun linkView_wheNoMoviePagesInContext_retrievesInitialPage() {
        //--prepare
        `when`(moviesContext.hasMoviePages()).thenReturn(false)
        `when`(moviesContext.isConfigCompleted()).thenReturn(true)
        `when`(moviesContext.movieGenres).thenReturn(getUIGenresList())

        //-- execute
        subject.linkView(playingMoviesView)

        //-- verify
        argumentCaptor<MoviesInTheaterInputParam>().apply {
            verify(playingMoviesUseCase).execute(capture())
            Assert.assertEquals(1, firstValue.page)
            Assert.assertEquals(4, firstValue.genres.size)
        }
    }


    @Test
    fun linkView_wheNoMoviePagesInContext_andContextNotCompleted_backToSplashScreen() {
        //--prepare
        `when`(moviesContext.hasMoviePages()).thenReturn(false)
        `when`(moviesContext.isConfigCompleted()).thenReturn(false)

        //-- execute
        subject.linkView(playingMoviesView)

        //-- verify
        verify(playingMoviesView).backToSplashScreen()
    }


    @Test
    fun getNextMoviePage_retrievesNextMoviePage() {
        //--prepare
        subject.linkView(playingMoviesView)

        `when`(moviesContext.isConfigCompleted()).thenReturn(true)
        `when`(moviesContext.movieGenres).thenReturn(getUIGenresList())

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
        argumentCaptor<MoviesInTheaterInputParam>().apply {
            verify(playingMoviesUseCase).execute(capture())
            Assert.assertEquals(5, firstValue.page)
            Assert.assertEquals(4, firstValue.genres.size)
        }
    }

    @Test
    fun getNextMoviePage_whenInteractorIsNotIdle_doesNothing() {
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
    fun createNextUseCaseParam_whenNoGenres_showsUnexpectedError() {
        //--prepare
        subject.linkView(playingMoviesView)
        `when`(moviesContext.movieGenres).thenReturn(null)
        `when`(moviesContext.getAllMoviePages()).thenReturn(listOf())

        //--execute
        val result = subject.createNextUseCaseParam()

        //--verify
        verify(playingMoviesView).showUnexpectedError()
        Assert.assertNull(result)
    }

    @Test
    fun createNextUseCaseParam_whenNoMorePages_showsEndOfPaging() {
        //--prepare
        subject.linkView(playingMoviesView)

        `when`(moviesContext.isConfigCompleted()).thenReturn(true)
        `when`(moviesContext.movieGenres).thenReturn(getUIGenresList())

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


    @Test
    fun executeUseCase_showsLoadingOnlyIfNeeded_executesSuccessfulUseCase_processResult() {
        //--prepare
        subject.linkView(playingMoviesView)
        val param = MoviesInTheaterInputParam(1, mapper.convertUiGenresToDomainGenres(getUIGenresList()))

        val domainMoviePage = mockDomainMoviePage()
        `when`(playingMoviesUseCase.execute(param)).thenReturn(domainMoviePage)
        `when`(playingMoviesView.getScreenWidth()).thenReturn(200)

        val imageConfiguration = ImageConfiguration("url", "210", 210)
        `when`(moviesContext.getImageConfigForScreenWidth(200)).thenReturn(imageConfiguration)


        //-- execute
        subject.executeUseCase(param)

        //--verify
        verify(playingMoviesView).showInitialLoading()
        verify(playingMoviesUseCase).execute(param)
        verify(moviesContext).getImageConfigForScreenWidth(200)


        argumentCaptor<UiMoviePage>().apply {
            verify(moviesContext).addMoviePage(capture())

            // this is testing the mapper
            Assert.assertEquals(1, firstValue.page)
            Assert.assertEquals(3, firstValue.results.size)
            Assert.assertEquals(200, firstValue.totalPages)
            Assert.assertEquals(201, firstValue.totalResults)

            verify(playingMoviesView).showMoviePage(firstValue)
        }
    }


    private fun getUIGenresList() = listOf(
            MovieGenre(1, "genre1"),
            MovieGenre(2, "genre2"),
            MovieGenre(3, "genre3"),
            MovieGenre(4, "genre4")
    )


    private fun mockDomainMoviePage() = DomainMoviePage(1,
            mockDomainMovieList(),
            200,
            201)


    private fun mockDomainMovieList() = listOf(
            DomainMovie(1.toDouble(),
                    "One",
                    "One",
                    "OverviewOne",
                    "ReleaseDateOne",
                    "OriginalLanguage1",
                    "PosterPathOne",
                    "backdropPathOne",
                    listOf(Genre(1, "Genre1")),
                    12.toDouble(),
                    12F,
                    12F),
            DomainMovie(2.toDouble(),
                    "Two",
                    "Two",
                    "OverviewTwo",
                    "ReleaseDateTwo",
                    "OriginalLanguage2",
                    "PosterPathTwo",
                    "backdropPathTwo",
                    listOf(Genre(2, "Genre2")),
                    12.toDouble(),
                    12F,
                    12F),
            DomainMovie(3.toDouble(),
                    "Three",
                    "Three",
                    "OverviewThree",
                    "ReleaseDateThree",
                    "OriginalLanguage3",
                    "PosterPathThree",
                    "backdropPathThree",
                    listOf(Genre(3, "Genre3")),
                    12.toDouble(),
                    12F,
                    12F)
    )

}
