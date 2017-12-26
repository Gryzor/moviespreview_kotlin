package com.jpp.moviespreview.app.ui.detail

import com.jpp.moviespreview.app.mock
import com.jpp.moviespreview.app.ui.Movie
import com.jpp.moviespreview.app.ui.MoviesContext
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify


/**
 * Espresso tests for this presenter is hard, that's why unit tests are added.
 *
 * Created by jpp on 12/15/17.
 */
class MovieDetailImagesPresenterImplTest {

    private lateinit var moviesContext: MoviesContext
    private lateinit var selectedMovie: Movie
    private lateinit var subject: MovieDetailImagesPresenterImpl
    private lateinit var movieDetailImagesView: MovieDetailImagesView

    @Before
    fun setUp() {
        selectedMovie = mock()
        moviesContext = mock()
        movieDetailImagesView = mock()
        subject = MovieDetailImagesPresenterImpl(moviesContext)
    }


    @Test
    fun onMovieImageSelected_updatesSelectedMovie() {
        `when`(moviesContext.selectedMovie).thenReturn(selectedMovie)
        subject.onMovieImageSelected(2)
        verify(selectedMovie).currentImageShown = 2
    }

    @Test
    fun linkView_showMovieImages_andMovieTitle() {
        `when`(moviesContext.selectedMovie).thenReturn(selectedMovie)
        val expectedImageList = listOf<String>()
        val expectedCurrentImage = 2
        val expectedMovieTitle = "Title"
        `when`(selectedMovie.images).thenReturn(expectedImageList)
        `when`(selectedMovie.currentImageShown).thenReturn(expectedCurrentImage)
        `when`(selectedMovie.title).thenReturn(expectedMovieTitle)

        subject.linkView(movieDetailImagesView)

        verify(movieDetailImagesView).showMovieImages(expectedImageList, expectedCurrentImage)
        verify(movieDetailImagesView).showMovieTitle(expectedMovieTitle)
    }


    @Test
    fun linkView_whenNoImageSelected_showsMovieNotSelected() {
        `when`(moviesContext.selectedMovie).thenReturn(null)

        subject.linkView(movieDetailImagesView)

        verify(movieDetailImagesView).showMovieNotSelected()
    }

}