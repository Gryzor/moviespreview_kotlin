package com.jpp.moviespreview.app.ui.sections.splash

import com.jpp.moviespreview.app.domain.Command
import com.jpp.moviespreview.app.domain.CommandData
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.Error
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractor
import com.jpp.moviespreview.app.util.extentions.whenNotNull
import com.jpp.moviespreview.app.domain.Genre as DomainGenre
import com.jpp.moviespreview.app.domain.MoviesConfiguration as DomainMoviesConfiguration

/**
 * [SplashPresenterInteractor] implementation. Interacts with the domain layer to retrieve the initial
 * movies configuration and all the movie genres.
 * When [retrieveConfiguration] is called, executes the commands, adapts the domain data result
 * to UI layer results and stores the results in the provided [SplashData].
 * This class does not handles multi-threading.
 *
 * Created by jpp on 2/13/18.
 */
class SplashPresenterInteractorImpl(private val mapper: DomainToUiDataMapper,
                                    private val connectivityInteractor: ConnectivityInteractor,
                                    private val retrieveConfigurationCommand: Command<Any, DomainMoviesConfiguration>,
                                    private val retrieveGenresCommand: Command<Any, List<DomainGenre>>) : SplashPresenterInteractor {


    private val retrieveConfigData = CommandData<DomainMoviesConfiguration>({ onConfigRetrieveSuccess() }, { onConfigRetrieveFailed() })
    private val retrieveGenresData = CommandData<List<DomainGenre>>({ onGenresRetrieveSuccess() }, { onConfigRetrieveFailed() })
    private lateinit var splashData: SplashData


    override fun retrieveConfiguration(splashData: SplashData) {
        this.splashData = splashData
        retrieveConfigurationCommand.execute(retrieveConfigData)
        retrieveGenresCommand.execute(retrieveGenresData)
    }


    private fun onConfigRetrieveSuccess() {
        with(mapper) {
            whenNotNull(retrieveConfigData.value, {
                splashData.posterConfig = convertPosterImageConfigurations(it)
                splashData.profileConfig = convertProfileImageConfigurations(it)
            })
        }
    }

    private fun onGenresRetrieveSuccess() {
        with(mapper) {
            whenNotNull(retrieveGenresData.value, {
                splashData.movieGenres = convertDomainGenresIntoUiGenres(it)
            })
        }
    }

    private fun onConfigRetrieveFailed() {
        with(connectivityInteractor) {
            if (isConnectedToNetwork()) {
                splashData.error = Error(Error.UNKNOWN)
            } else {
                splashData.error = Error(Error.NO_CONNECTION)
            }
        }
    }

}