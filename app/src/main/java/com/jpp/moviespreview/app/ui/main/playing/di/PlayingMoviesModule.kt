package com.jpp.moviespreview.app.ui.main.playing.di

import com.jpp.moviespreview.app.domain.MoviePage
import com.jpp.moviespreview.app.domain.MoviesInTheaterInputParam
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.interactors.BackgroundInteractor
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractor
import com.jpp.moviespreview.app.ui.interactors.PresenterInteractorDelegate
import com.jpp.moviespreview.app.ui.interactors.PresenterInteractorDelegateImpl
import com.jpp.moviespreview.app.ui.main.di.MainScreenScope
import com.jpp.moviespreview.app.ui.main.playing.PlayingMoviesPresenter
import com.jpp.moviespreview.app.ui.main.playing.PlayingMoviesPresenterImpl
import dagger.Module
import dagger.Provides

/**
 * Provides dependencies for the playing movies in theater section
 *
 * Created by jpp on 10/23/17.
 */
@Module
class PlayingMoviesModule {


    //TODO these should be fragment scoped

    @Provides
    @MainScreenScope
    fun providesPlayingMoviesPresenter(moviesContext: MoviesContext,
                                       presenterInteractorDelegate: PresenterInteractorDelegate,
                                       playingMoviesUseCase: UseCase<MoviesInTheaterInputParam, MoviePage>,
                                       mapper: DomainToUiDataMapper): PlayingMoviesPresenter
            = PlayingMoviesPresenterImpl(moviesContext, presenterInteractorDelegate, playingMoviesUseCase, mapper)


    @Provides
    @MainScreenScope
    fun providesPlayingMoviesInteractorDelegate(backgroundInteractor: BackgroundInteractor,
                                                connectivityInteractor: ConnectivityInteractor): PresenterInteractorDelegate
            = PresenterInteractorDelegateImpl(backgroundInteractor, connectivityInteractor)

}