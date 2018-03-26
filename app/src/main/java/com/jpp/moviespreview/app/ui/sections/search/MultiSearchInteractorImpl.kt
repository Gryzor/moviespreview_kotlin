package com.jpp.moviespreview.app.ui.sections.search

import com.jpp.moviespreview.app.domain.Command
import com.jpp.moviespreview.app.domain.CommandData
import com.jpp.moviespreview.app.domain.MultiSearchPage
import com.jpp.moviespreview.app.domain.MultiSearchParam
import com.jpp.moviespreview.app.ui.*
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractor
import com.jpp.moviespreview.app.util.extentions.whenFalse
import com.jpp.moviespreview.app.util.extentions.whenNotNull
import com.jpp.moviespreview.app.domain.Genre as DomainGenre

/**
 * [MultiSearchInteractor] implementation. Interacts with the domain layer to execute a query
 * and return the value as UI layer language.
 * It's important to call [configure] before doing something with this interactor.
 * Created by jpp on 3/17/18.
 */
class MultiSearchInteractorImpl(private val mapper: DomainToUiDataMapper,
                                private val connectivityInteractor: ConnectivityInteractor,
                                private val command: Command<MultiSearchParam, MultiSearchPage>) : MultiSearchInteractor {


    private val commandData = CommandData<MultiSearchPage>({ onSearchResultSuccess() }, { onSearchResultError() })

    private lateinit var searchData: MultiSearchData
    private lateinit var domainMovieGenres: List<DomainGenre>
    private lateinit var uiMovieGenres: List<MovieGenre>
    private lateinit var posterImageConfiguration: PosterImageConfiguration
    private lateinit var profileImageConfig: ProfileImageConfiguration

    override fun configure(data: MultiSearchData,
                           movieGenres: List<MovieGenre>,
                           posterImageConfig: PosterImageConfiguration,
                           profileImageConfig: ProfileImageConfiguration) {
        whenFalse(this::domainMovieGenres.isInitialized, { domainMovieGenres = mapper.convertUiGenresIntoDomainGenres(movieGenres) })
        whenFalse(this::searchData.isInitialized, { searchData = data })
        whenFalse(this::uiMovieGenres.isInitialized, { uiMovieGenres = movieGenres })
        whenFalse(this::posterImageConfiguration.isInitialized, { this.posterImageConfiguration = posterImageConfig })
        whenFalse(this::profileImageConfig.isInitialized, { this.profileImageConfig = profileImageConfig })
    }

    override fun searchFirstPage(query: String) {
        searchPage(query, 1)
    }

    override fun searchPage(query: String, page: Int) {
        verifyConfigAndFailIfNot()
        command.execute(commandData, MultiSearchParam(query, page, domainMovieGenres))
    }

    private fun verifyConfigAndFailIfNot() {
        whenFalse(this::domainMovieGenres.isInitialized, { throw IllegalStateException("You need to configure this object before interacting with it.") })
        whenFalse(this::searchData.isInitialized, { throw IllegalStateException("You need to configure this object before interacting with it.") })
        whenFalse(this::uiMovieGenres.isInitialized, { throw IllegalStateException("You need to configure this object before interacting with it.") })
        whenFalse(this::posterImageConfiguration.isInitialized, { throw IllegalStateException("You need to configure this object before interacting with it.") })
        whenFalse(this::profileImageConfig.isInitialized, { throw IllegalStateException("You need to configure this object before interacting with it.") })
    }


    private fun onSearchResultSuccess() {
        whenNotNull(commandData.value) {
            searchData.lastSearchPage = mapper.convertDomainResultPageInUiResultPage(it, posterImageConfiguration, profileImageConfig, uiMovieGenres)
        }
    }

    private fun onSearchResultError() {
        with(connectivityInteractor) {
            when (isConnectedToNetwork()) {
                true -> searchData.error = Error(Error.UNKNOWN)
                else -> searchData.error = Error(Error.NO_CONNECTION)
            }
        }
    }
}