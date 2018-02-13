package com.jpp.moviespreview.app.ui.sections.splash

import com.jpp.moviespreview.app.domain.Command
import com.jpp.moviespreview.app.domain.CommandData
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.Error
import com.jpp.moviespreview.app.ui.interactors.BackgroundExecutor
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractor
import com.jpp.moviespreview.app.domain.MoviesConfiguration as DomainMoviesConfiguration

/**
 * Created by jpp on 2/13/18.
 */
class SplashPresenterInteractorImpl(private val backgroundExecutor: BackgroundExecutor,
                                    private val mapper: DomainToUiDataMapper,
                                    private val connectivityInteractor: ConnectivityInteractor,
                                    private val retrieveConfigurationCommand: Command<Any, DomainMoviesConfiguration>) : SplashPresenterInteractor {


    private val retrieveConfigData = CommandData<DomainMoviesConfiguration>({ onConfigRetrieveSuccess() }, { onConfigRetrieveFailed() })
    private val notifyCompleted = { backgroundExecutor.executeUiJob { splashData.isCompleted = true } }
    private lateinit var splashData: SplashData


    override fun retrieveConfiguration(splashData: SplashData) {
        this.splashData = splashData
        backgroundExecutor.executeBackgroundJob { retrieveConfigurationCommand.execute(retrieveConfigData) }
    }


    private fun onConfigRetrieveSuccess() {
        splashData.posterConfig = mapper.convertPosterImageConfigurations(retrieveConfigData.value)
        splashData.profileConfig = mapper.convertProfileImageConfigurations(retrieveConfigData.value)
        notifyCompleted()
    }

    private fun onConfigRetrieveFailed() {
        with(connectivityInteractor) {
            if (isConnectedToNetwork()) {
                splashData.error = Error(Error.UNKNOWN)
            } else {
                splashData.error = Error(Error.NO_CONNECTION)
            }
            notifyCompleted()
        }
    }

}