package com.jpp.moviespreview.app.ui.sections.main.playing.di

import com.jpp.moviespreview.app.domain.MoviePage
import com.jpp.moviespreview.app.domain.PageParam
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.interactors.ImageConfigurationInteractor
import com.jpp.moviespreview.app.ui.interactors.ImageConfigurationInteractorImpl
import com.jpp.moviespreview.app.ui.interactors.PaginationInteractor
import com.jpp.moviespreview.app.ui.interactors.PresenterInteractorDelegate
import com.jpp.moviespreview.app.ui.sections.main.di.MainScreenScope
import com.jpp.moviespreview.app.ui.sections.main.playing.PlayingMoviesPresenter
import com.jpp.moviespreview.app.ui.sections.main.playing.PlayingMoviesPresenterImpl
import com.jpp.moviespreview.app.ui.sections.main.playing.PlayingMoviesPresenterInteractor
import com.jpp.moviespreview.app.ui.sections.main.playing.PlayingMoviesPresenterInteractorImpl
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
                                       presenterInteractorDelegate: PlayingMoviesPresenterInteractor,
                                       playingMoviesUseCase: UseCase<PageParam, MoviePage>,
                                       mapper: DomainToUiDataMapper): PlayingMoviesPresenter
            = PlayingMoviesPresenterImpl(moviesContext, presenterInteractorDelegate, playingMoviesUseCase, mapper)


    @Provides
    @MainScreenScope
    fun providesPlayingMoviesInteractorDelegate(presenterInteractorDelegate: PresenterInteractorDelegate,
                                                imageConfigurationInteractor: ImageConfigurationInteractor,
                                                paginationInteractor: PaginationInteractor): PlayingMoviesPresenterInteractor
            = PlayingMoviesPresenterInteractorImpl(presenterInteractorDelegate, imageConfigurationInteractor, paginationInteractor)

    @Provides
    @MainScreenScope
    fun providesImageConfigurationPresenterDelegate(): ImageConfigurationInteractor
            = ImageConfigurationInteractorImpl()

}