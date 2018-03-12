package com.jpp.moviespreview.app.ui.sections.detail.credits.di

import com.jpp.moviespreview.app.di.fragment.FragmentComponent
import com.jpp.moviespreview.app.di.fragment.FragmentComponentBuilder
import com.jpp.moviespreview.app.di.fragment.FragmentModule
import com.jpp.moviespreview.app.di.fragment.FragmentScope
import com.jpp.moviespreview.app.domain.Command
import com.jpp.moviespreview.app.domain.Movie
import com.jpp.moviespreview.app.domain.MovieCredits
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.MoviesContextHandler
import com.jpp.moviespreview.app.ui.interactors.BackgroundExecutor
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractor
import com.jpp.moviespreview.app.ui.interactors.ImageConfigurationManager
import com.jpp.moviespreview.app.ui.interactors.ImageConfigurationManagerImpl
import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailCreditsInteractor
import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailCreditsPresenter
import com.jpp.moviespreview.app.ui.sections.detail.credits.MovieCreditsFragment
import com.jpp.moviespreview.app.ui.sections.detail.credits.MovieDetailCreditsInteractorImpl
import com.jpp.moviespreview.app.ui.sections.detail.credits.MovieDetailCreditsPresenterImpl
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

/**
 * Provides dependencies for [MovieCreditsFragment]
 *
 * Created by jpp on 3/2/18.
 */
@FragmentScope
@Subcomponent(modules = [(MovieCreditsFragmentComponenet.MovieCreditsFragmentModule::class)])
interface MovieCreditsFragmentComponenet : FragmentComponent<MovieCreditsFragment> {

    @Subcomponent.Builder
    interface Builder : FragmentComponentBuilder<MovieCreditsFragmentModule, MovieCreditsFragmentComponenet>

    @Module
    class MovieCreditsFragmentModule internal constructor(fragment: MovieCreditsFragment) : FragmentModule<MovieCreditsFragment>(fragment) {


        @Provides
        @FragmentScope
        fun providesMovieDetailsCreditsPresenter(moviesContextHandler: MoviesContextHandler,
                                                 backgroundExecutor: BackgroundExecutor,
                                                 interactor: MovieDetailCreditsInteractor,
                                                 imageConfigManager: ImageConfigurationManager)
                : MovieDetailCreditsPresenter = MovieDetailCreditsPresenterImpl(moviesContextHandler, backgroundExecutor, interactor, imageConfigManager)


        @Provides
        @FragmentScope
        fun providesMovieDetailCreditsInteractor(mapper: DomainToUiDataMapper,
                                                 connectivityInteractor: ConnectivityInteractor,
                                                 retrieveMovieCreditsCommand: Command<Movie, MovieCredits>)
                : MovieDetailCreditsInteractor = MovieDetailCreditsInteractorImpl(mapper, connectivityInteractor, retrieveMovieCreditsCommand)

        @Provides
        @FragmentScope
        fun providesImageConfigurationManager(): ImageConfigurationManager = ImageConfigurationManagerImpl()

    }


}