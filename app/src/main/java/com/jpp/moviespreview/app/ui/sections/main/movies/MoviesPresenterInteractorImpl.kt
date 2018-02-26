package com.jpp.moviespreview.app.ui.sections.main.movies

import com.jpp.moviespreview.app.domain.Command
import com.jpp.moviespreview.app.domain.CommandData
import com.jpp.moviespreview.app.domain.PageParam
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.Error
import com.jpp.moviespreview.app.ui.MovieGenre
import com.jpp.moviespreview.app.ui.PosterImageConfiguration
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractor
import com.jpp.moviespreview.app.util.extentions.whenFalse
import com.jpp.moviespreview.app.util.extentions.whenNotNull
import com.jpp.moviespreview.app.domain.Genre as DomainGenre
import com.jpp.moviespreview.app.domain.MoviePage as DomainMoviePage

/**
 * [MoviesPresenterInteractor] implementation. Interacts with the domain layer to retrieve
 * movie pages.
 * It's important to call [configure] before doing something with this interactor.
 * When [retrieveMoviePage] is called, it will execute the command and retrieve the [DomainMoviePage]
 * identified by the provided page number.
 * Will notify it's client using the provided [MoviesData] callback.
 * Created by jpp on 2/19/18.
 */
class MoviesPresenterInteractorImpl(private val mapper: DomainToUiDataMapper,
                                    private val connectivityInteractor: ConnectivityInteractor,
                                    private val retrieveMoviePageCommand: Command<PageParam, DomainMoviePage>) : MoviesPresenterInteractor {


    private val commandData = CommandData<DomainMoviePage>({ onPageRetrieveSuccess() }, { onPageRetrieveFail() })
    private lateinit var domainMovieGenres: List<DomainGenre>

    private lateinit var moviesData: MoviesData
    private lateinit var posterImageConfiguration: PosterImageConfiguration
    private lateinit var uiMovieGenres: List<MovieGenre>


    override fun configure(data: MoviesData, movieGenres: List<MovieGenre>, posterImageConfiguration: PosterImageConfiguration) {
        whenFalse(this::domainMovieGenres.isInitialized, { domainMovieGenres = mapper.convertUiGenresIntoDomainGenres(movieGenres) })
        whenFalse(this::moviesData.isInitialized, { moviesData = data })
        whenFalse(this::uiMovieGenres.isInitialized, { uiMovieGenres = movieGenres })
        whenFalse(this::posterImageConfiguration.isInitialized, { this.posterImageConfiguration = posterImageConfiguration })
    }

    override fun retrieveMoviePage(page: Int) {
        verifyConfigAndFailIfNot()
        retrieveMoviePageCommand.execute(commandData, PageParam(page, domainMovieGenres))
    }

    private fun verifyConfigAndFailIfNot() {
        whenFalse(this::domainMovieGenres.isInitialized, { throw IllegalStateException("You need to configure this object before interacting with it.") })
        whenFalse(this::moviesData.isInitialized, { throw IllegalStateException("You need to configure this object before interacting with it.") })
        whenFalse(this::uiMovieGenres.isInitialized, { throw IllegalStateException("You need to configure this object before interacting with it.") })
        whenFalse(this::posterImageConfiguration.isInitialized, { throw IllegalStateException("You need to configure this object before interacting with it.") })
    }


    private fun onPageRetrieveSuccess() {
        whenNotNull(commandData.value) {
            moviesData.lastMoviePage = mapper.convertDomainMoviePageToUiMoviePage(it, posterImageConfiguration, uiMovieGenres)
        }
    }


    private fun onPageRetrieveFail() {
        with(connectivityInteractor) {
            if (isConnectedToNetwork()) {
                moviesData.error = Error(Error.UNKNOWN)
            } else {
                moviesData.error = Error(Error.NO_CONNECTION)
            }
        }
    }

}