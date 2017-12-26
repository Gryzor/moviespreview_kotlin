package com.jpp.moviespreview.app.ui.detail.body

import com.jpp.moviespreview.app.mock
import com.jpp.moviespreview.app.ui.Movie
import com.jpp.moviespreview.app.ui.MovieGenre
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.detail.MovieDetailView
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

/**
 * Created by jpp on 12/15/17.
 */
class MovieDetailPresenterImplTest {

//    private lateinit var moviesContext: MoviesContext
//    private lateinit var selectedMovie: Movie
//    private lateinit var subject: MovieDetailPresenterImpl
//    private lateinit var view: MovieDetailView
//
//    @Before
//    fun setUp() {
//        moviesContext = mock()
//        selectedMovie = mock()
//        view = mock()
//        subject = MovieDetailPresenterImpl(moviesContext)
//    }
//
//    @Test
//    fun linkView_showsMovieDetails() {
//        `when`(moviesContext.selectedMovie).thenReturn(selectedMovie)
//        val expectedOverview = "Overview"
//        val expectedGenres= listOf<MovieGenre>()
//        val expectedVoteCount = 12.toDouble()
//        val expectedPopularity = 12.toFloat()
//        `when`(selectedMovie.overview).thenReturn(expectedOverview)
//        `when`(selectedMovie.genres).thenReturn(expectedGenres)
//        `when`(selectedMovie.voteCount).thenReturn(expectedVoteCount)
//        `when`(selectedMovie.popularity).thenReturn(expectedPopularity)
//
//        subject.linkView(view)
//
//        verify(view).showMovieOverview(expectedOverview)
//        verify(view).showMovieGenres(expectedGenres)
//        verify(view).showMoviePopularity(expectedPopularity)
//        verify(view).showMovieVoteCount(expectedVoteCount)
//    }

}