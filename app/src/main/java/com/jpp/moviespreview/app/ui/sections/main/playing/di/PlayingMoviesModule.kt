package com.jpp.moviespreview.app.ui.sections.main.playing.di

import com.jpp.moviespreview.app.domain.MoviePage
import com.jpp.moviespreview.app.domain.PageParam
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.ApplicationMoviesContext
import com.jpp.moviespreview.app.ui.interactors.ImageConfigurationManager
import com.jpp.moviespreview.app.ui.interactors.ImageConfigurationManagerImpl
import com.jpp.moviespreview.app.ui.interactors.PaginationController
import com.jpp.moviespreview.app.ui.interactors.PresenterInteractorDelegate
import com.jpp.moviespreview.app.ui.sections.main.di.MainScreenScope
import com.jpp.moviespreview.app.ui.sections.main.playing.PlayingMoviesPresenter
import com.jpp.moviespreview.app.ui.sections.main.playing.PlayingMoviesPresenterImpl
import com.jpp.moviespreview.app.ui.sections.main.playing.PlayingMoviesPresenterController
import com.jpp.moviespreview.app.ui.sections.main.playing.PlayingMoviesPresenterControllerImpl
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
    fun providesPlayingMoviesPresenter(moviesContext: ApplicationMoviesContext,
                                       presenterInteractorDelegate: PlayingMoviesPresenterController,
                                       playingMoviesUseCase: UseCase<PageParam, MoviePage>,
                                       mapper: DomainToUiDataMapper): PlayingMoviesPresenter
            = PlayingMoviesPresenterImpl(moviesContext, presenterInteractorDelegate, playingMoviesUseCase, mapper)


    @Provides
    @MainScreenScope
    fun providesPlayingMoviesInteractorDelegate(presenterInteractorDelegate: PresenterInteractorDelegate,
                                                imageConfigurationManager: ImageConfigurationManager,
                                                paginationController: PaginationController): PlayingMoviesPresenterController
            = PlayingMoviesPresenterControllerImpl(presenterInteractorDelegate, imageConfigurationManager, paginationController)

    @Provides
    @MainScreenScope
    fun providesImageConfigurationManager(): ImageConfigurationManager
            = ImageConfigurationManagerImpl()

}