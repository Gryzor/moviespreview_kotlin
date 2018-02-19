package com.jpp.moviespreview.app.ui.sections.main.movies

import com.jpp.moviespreview.app.domain.Command
import com.jpp.moviespreview.app.domain.CommandData
import com.jpp.moviespreview.app.domain.PageParam
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.Error
import com.jpp.moviespreview.app.ui.MovieGenre
import com.jpp.moviespreview.app.ui.PosterImageConfiguration
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractor
import com.jpp.moviespreview.app.util.extentions.whenNotNull
import com.jpp.moviespreview.app.util.extentions.whenNull
import com.jpp.moviespreview.app.domain.Genre as DomainGenre
import com.jpp.moviespreview.app.domain.MoviePage as DomainMoviePage

/**
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
        whenNull(domainMovieGenres, { domainMovieGenres = mapper.convertUiGenresIntoDomainGenres(movieGenres) })
        whenNull(moviesData, { moviesData = data })
        whenNull(uiMovieGenres, { uiMovieGenres = movieGenres })
        whenNull(posterImageConfiguration, { this.posterImageConfiguration = posterImageConfiguration })
    }

    override fun retrieveMoviePage(page: Int) {
        verifyConfigAndFailIfNot()
        retrieveMoviePageCommand.execute(commandData, PageParam(page, domainMovieGenres))
    }

    private fun verifyConfigAndFailIfNot() {
        whenNull(domainMovieGenres, { throw IllegalStateException("You need to configure this object before interacting with it.") })
        whenNull(moviesData, { throw IllegalStateException("You need to configure this object before interacting with it.") })
        whenNull(uiMovieGenres, { throw IllegalStateException("You need to configure this object before interacting with it.") })
        whenNull(posterImageConfiguration, { throw IllegalStateException("You need to configure this object before interacting with it.") })
    }


    private fun onPageRetrieveSuccess() {
        with(mapper) {
            whenNotNull(commandData.value) {
                moviesData.lastMoviePage = convertDomainMoviePageToUiMoviePage(it, posterImageConfiguration, uiMovieGenres)
            }
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