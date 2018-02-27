package com.jpp.moviespreview.app.ui


import com.jpp.moviespreview.app.mockMovieGenres
import com.jpp.moviespreview.app.mockPosterImageConfig
import com.jpp.moviespreview.app.mockProfileImageConfig
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Created by jpp on 10/27/17.
 */
class ApplicationMoviesContextTest {

    private lateinit var subject: ApplicationMoviesContext

    @Before
    fun setUp() {
        subject = ApplicationMoviesContext()
    }

    @Test
    fun isConfigCompleted1() {
        subject.posterImageConfig = mockPosterImageConfig()
        assertFalse(subject.isConfigCompleted())
    }

    @Test
    fun isConfigCompleted2() {
        subject.posterImageConfig = mockPosterImageConfig()
        subject.profileImageConfig = mockProfileImageConfig()
        assertFalse(subject.isConfigCompleted())
    }

    @Test
    fun isConfigCompleted3() {
        subject.posterImageConfig = mockPosterImageConfig()
        subject.profileImageConfig = mockProfileImageConfig()
        subject.movieGenres = mockMovieGenres()
        assertTrue(subject.isConfigCompleted())
    }

    @Test
    fun isConfigCompleted4() {
        subject.profileImageConfig = mockProfileImageConfig()
        subject.movieGenres = mockMovieGenres()
        assertFalse(subject.isConfigCompleted())
    }

    @Test
    fun isConfigCompleted5() {
        subject.movieGenres = mockMovieGenres()
        assertFalse(subject.isConfigCompleted())
    }

    @Test
    fun isConfigCompleted6() {
        subject.posterImageConfig = mockPosterImageConfig()
        subject.movieGenres = mockMovieGenres()
        assertFalse(subject.isConfigCompleted())
    }
}