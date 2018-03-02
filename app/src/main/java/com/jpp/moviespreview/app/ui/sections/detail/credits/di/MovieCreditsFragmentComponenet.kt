package com.jpp.moviespreview.app.ui.sections.detail.credits.di

import com.jpp.moviespreview.app.di.fragment.FragmentComponent
import com.jpp.moviespreview.app.di.fragment.FragmentComponentBuilder
import com.jpp.moviespreview.app.di.fragment.FragmentModule
import com.jpp.moviespreview.app.di.fragment.FragmentScope
import com.jpp.moviespreview.app.domain.Movie
import com.jpp.moviespreview.app.domain.MovieCredits
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.ui.ApplicationMoviesContext
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.interactors.ImageConfigurationManager
import com.jpp.moviespreview.app.ui.interactors.ImageConfigurationManagerImpl
import com.jpp.moviespreview.app.ui.interactors.PresenterInteractorDelegate
import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailCreditsPresenter
import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailsCreditsPresenterInteractor
import com.jpp.moviespreview.app.ui.sections.detail.credits.MovieCreditsFragment
import com.jpp.moviespreview.app.ui.sections.detail.credits.MovieDetailCreditsPresenterImpl
import com.jpp.moviespreview.app.ui.sections.detail.credits.MovieDetailsCreditsPresenterInteractorImpl
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
        fun providesMovieDetailsCreditsPresenter(moviesContext: ApplicationMoviesContext,
                                                 presenterInteractorDelegate: MovieDetailsCreditsPresenterInteractor,
                                                 useCase: UseCase<Movie, MovieCredits>,
                                                 mapper: DomainToUiDataMapper): MovieDetailCreditsPresenter = MovieDetailCreditsPresenterImpl(moviesContext, presenterInteractorDelegate, useCase, mapper)

        @Provides
        @FragmentScope
        fun providesMovieDetailsCreditsPresenterInteractor(presenterInteractorDelegate: PresenterInteractorDelegate,
                                                           imageConfigurationManager: ImageConfigurationManager): MovieDetailsCreditsPresenterInteractor = MovieDetailsCreditsPresenterInteractorImpl(presenterInteractorDelegate, imageConfigurationManager)

        @Provides
        @FragmentScope
        fun providesImageConfigurationManager(): ImageConfigurationManager = ImageConfigurationManagerImpl()

    }


}