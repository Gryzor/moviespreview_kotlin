package com.jpp.moviespreview.app.ui.sections.detail.credits

import com.jpp.moviespreview.app.domain.Command
import com.jpp.moviespreview.app.domain.CommandData
import com.jpp.moviespreview.app.mock
import com.jpp.moviespreview.app.ui.*
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractor
import com.jpp.moviespreview.app.ui.sections.detail.CreditsData
import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailCreditsInteractor
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import com.jpp.moviespreview.app.domain.CastCharacter as DomainCastCharacter
import com.jpp.moviespreview.app.domain.CrewPerson as DomainCrewPerson
import com.jpp.moviespreview.app.domain.Movie as DomainMovie
import com.jpp.moviespreview.app.domain.MovieCredits as DomainMovieCredits

/**
 * Created by jpp on 3/13/18.
 */
class MovieDetailCreditsInteractorTest {

    private lateinit var mapper: DomainToUiDataMapper
    private lateinit var connectivityInteractor: ConnectivityInteractor
    private lateinit var retrieveMovieCreditsCommand: Command<DomainMovie, DomainMovieCredits>
    private lateinit var data: CreditsData
    private lateinit var subject: MovieDetailCreditsInteractor


    @Before
    fun setUp() {
        mapper = mock()
        connectivityInteractor = mock()
        retrieveMovieCreditsCommand = mock()

        data = CreditsData()

        subject = MovieDetailCreditsInteractorImpl(mapper, connectivityInteractor, retrieveMovieCreditsCommand)
    }


    @Test
    fun retrieveMovieCreditsConvertsMovieAndExecutesCommand() {
        val profileImageConfig: ProfileImageConfiguration = mock()
        val movie: Movie = mock()
        val domainMovie: DomainMovie = mock()

        `when`(mapper.convertUiMovieIntoDomainMovie(movie)).thenReturn(domainMovie)

        subject.retrieveMovieCredits(data, movie, profileImageConfig)

        verify(retrieveMovieCreditsCommand).execute(any(), any())
    }


    @Test
    fun retrieveMovieCreditsSuccess() {
        val profileImageConfig: ProfileImageConfiguration = mock()
        val movie: Movie = mock()
        val domainMovie: DomainMovie = mock()
        val domainMovieCredits: DomainMovieCredits = mock()
        val domainCast: List<DomainCastCharacter> = listOf()
        val domainCrew: List<DomainCrewPerson> = listOf()
        val expectedCast: List<CreditPerson> = mock()


        `when`(mapper.convertUiMovieIntoDomainMovie(movie)).thenReturn(domainMovie)
        `when`(domainMovieCredits.cast).thenReturn(domainCast)
        `when`(domainMovieCredits.crew).thenReturn(domainCrew)
        `when`(mapper.convertDomainCreditsInUiCredits(domainCast, domainCrew, profileImageConfig)).thenReturn(expectedCast)

        doAnswer {
            val param = it.arguments[1] as DomainMovie
            assertEquals(domainMovie, param)

            val callback = it.arguments[0] as CommandData<DomainMovieCredits>
            callback.value = domainMovieCredits

        }.`when`(retrieveMovieCreditsCommand).execute(any(), any())

        subject.retrieveMovieCredits(data, movie, profileImageConfig)

        assertEquals(expectedCast, data.credits)
        assertNull(data.error)
    }

    @Test
    fun retrieveMovieNotifiesConnectivityError() {
        val profileImageConfig: ProfileImageConfiguration = mock()
        val movie: Movie = mock()
        val domainMovie: DomainMovie = mock()

        `when`(mapper.convertUiMovieIntoDomainMovie(movie)).thenReturn(domainMovie)
        `when`(connectivityInteractor.isConnectedToNetwork()).thenReturn(false)

        doAnswer {
            val callback = it.arguments[0] as CommandData<DomainMovieCredits>
            callback.error = mock() //type not important
        }.`when`(retrieveMovieCreditsCommand).execute(any(), any())

        subject.retrieveMovieCredits(data, movie, profileImageConfig)

        assertNull(data.credits)
        assertNotNull(data.error)
        assertEquals(data.error!!.type, Error.NO_CONNECTION)
    }

    @Test
    fun retrieveMovieNotifiesUnknownError() {
        val profileImageConfig: ProfileImageConfiguration = mock()
        val movie: Movie = mock()
        val domainMovie: DomainMovie = mock()

        `when`(mapper.convertUiMovieIntoDomainMovie(movie)).thenReturn(domainMovie)
        `when`(connectivityInteractor.isConnectedToNetwork()).thenReturn(true)

        doAnswer {
            val callback = it.arguments[0] as CommandData<DomainMovieCredits>
            callback.error = mock() //type not important
        }.`when`(retrieveMovieCreditsCommand).execute(any(), any())

        subject.retrieveMovieCredits(data, movie, profileImageConfig)

        assertNull(data.credits)
        assertNotNull(data.error)
        assertEquals(data.error!!.type, Error.UNKNOWN)
    }
}