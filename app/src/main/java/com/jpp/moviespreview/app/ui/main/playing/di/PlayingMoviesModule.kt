package com.jpp.moviespreview.app.ui.main.playing.di

import com.jpp.moviespreview.app.data.cache.MoviesCache
import com.jpp.moviespreview.app.data.server.MoviesPreviewApiWrapper
import com.jpp.moviespreview.app.domain.MoviePage
import com.jpp.moviespreview.app.domain.MoviesInTheaterInputParam
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.domain.movie.MovieDataMapper
import com.jpp.moviespreview.app.domain.movie.RetrieveMoviesInTheaterUseCase
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.interactors.BackgroundInteractor
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractor
import com.jpp.moviespreview.app.ui.main.di.MainScreenScope
import com.jpp.moviespreview.app.ui.main.playing.PlayingMoviesInteractorDelegate
import com.jpp.moviespreview.app.ui.main.playing.PlayingMoviesInteractorDelegateImpl
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
                                       playingMoviesInteractorDelegate: PlayingMoviesInteractorDelegate,
                                       playingMoviesUseCase: UseCase<MoviesInTheaterInputParam, MoviePage>,
                                       mapper: DomainToUiDataMapper): PlayingMoviesPresenter
            = PlayingMoviesPresenterImpl(moviesContext, playingMoviesInteractorDelegate, playingMoviesUseCase, mapper)


    @Provides
    @MainScreenScope
    fun providesRetrieveMoviesInTheaterUseCase(api: MoviesPreviewApiWrapper, moviesCache: MoviesCache): UseCase<MoviesInTheaterInputParam, MoviePage>
            = RetrieveMoviesInTheaterUseCase(MovieDataMapper(), api, moviesCache)

    @Provides
    @MainScreenScope
    fun providesPlayingMoviesInteractorDelegate(backgroundInteractor: BackgroundInteractor,
                                                connectivityInteractor: ConnectivityInteractor): PlayingMoviesInteractorDelegate
            = PlayingMoviesInteractorDelegateImpl(backgroundInteractor, connectivityInteractor)

}