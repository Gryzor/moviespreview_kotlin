package com.jpp.moviespreview.app.ui.sections.detail.credits

import com.jpp.moviespreview.app.domain.Command
import com.jpp.moviespreview.app.domain.CommandData
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.Error
import com.jpp.moviespreview.app.ui.Movie
import com.jpp.moviespreview.app.ui.ProfileImageConfiguration
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractor
import com.jpp.moviespreview.app.ui.sections.detail.CreditsData
import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailCreditsInteractor
import com.jpp.moviespreview.app.util.extentions.whenNotNull
import com.jpp.moviespreview.app.domain.Movie as DomainMovie
import com.jpp.moviespreview.app.domain.MovieCredits as DomainMovieCredits

/**
 * [MovieDetailCreditsInteractor] implementation. Interacts with the domain layer to
 * retrieve the credits for a given [Movie].
 * When [retrieveMovieCredits] is called, it will execute the command and will communicate
 * woth the clients using the provided [CreditsData].
 * Created by jpp on 3/10/18.
 */
class MovieDetailCreditsInteractorImpl(private val mapper: DomainToUiDataMapper,
                                       private val connectivityInteractor: ConnectivityInteractor,
                                       private val retrieveMovieCreditsCommand: Command<DomainMovie, DomainMovieCredits>) : MovieDetailCreditsInteractor {


    private val commandData = CommandData<DomainMovieCredits>({ onMovieCreditsRetrievedSuccess() }, { onMovieCreditsRetrieveFail() })
    private lateinit var profileImageConfig: ProfileImageConfiguration
    private lateinit var creditsData: CreditsData


    override fun retrieveMovieCredits(creditsData: CreditsData, movie: Movie, profileImageConfig: ProfileImageConfiguration) {
        this.profileImageConfig = profileImageConfig
        this.creditsData = creditsData
        with(mapper) {
            convertUiMovieIntoDomainMovie(movie).let {
                retrieveMovieCreditsCommand.execute(commandData, it)
            }
        }
    }

    private fun onMovieCreditsRetrievedSuccess() {
        whenNotNull(commandData.value) {
            creditsData.credits = mapper.convertDomainCreditsInUiCredits(it.cast.sortedBy { it.order }, it.crew, profileImageConfig)
        }
    }

    private fun onMovieCreditsRetrieveFail() {
        with(connectivityInteractor) {
            when (isConnectedToNetwork()) {
                true -> creditsData.error = Error(Error.UNKNOWN)
                else -> creditsData.error = Error(Error.NO_CONNECTION)
            }
        }
    }
}