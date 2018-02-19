package com.jpp.moviespreview.app.ui.sections.detail.di

import com.jpp.moviespreview.app.domain.Movie
import com.jpp.moviespreview.app.domain.MovieCredits
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.ApplicationMoviesContext
import com.jpp.moviespreview.app.ui.interactors.ImageConfigurationInteractor
import com.jpp.moviespreview.app.ui.interactors.ImageConfigurationInteractorImpl
import com.jpp.moviespreview.app.ui.sections.detail.body.MovieDetailPresenterImpl
import com.jpp.moviespreview.app.ui.sections.detail.credits.MovieDetailCreditsPresenterImpl
import com.jpp.moviespreview.app.ui.sections.detail.credits.MovieDetailsCreditsPresenterInteractorImpl
import com.jpp.moviespreview.app.ui.interactors.PresenterInteractorDelegate
import com.jpp.moviespreview.app.ui.sections.detail.*
import dagger.Module
import dagger.Provides

/**
 * Provides dependencies for the movie details section.
 *
 * Created by jpp on 11/11/17.
 */
@Module
class MovieDetailsModule {

    @Provides
    @DetailsScope
    fun providesMovieDetailImagesPresenter(moviesContext: ApplicationMoviesContext): MovieDetailImagesPresenter =
            MovieDetailImagesPresenterImpl(moviesContext)


    @Provides
    @DetailsScope
    fun providesMovieDetailsPresenter(moviesContext: ApplicationMoviesContext): MovieDetailPresenter
            = MovieDetailPresenterImpl(moviesContext)

    @Provides
    @DetailsScope
    fun providesMovieDetailsCreditsPresenter(moviesContext: ApplicationMoviesContext,
                                             presenterInteractorDelegate: MovieDetailsCreditsPresenterInteractor,
                                             useCase: UseCase<Movie, MovieCredits>,
                                             mapper: DomainToUiDataMapper): MovieDetailCreditsPresenter
            = MovieDetailCreditsPresenterImpl(moviesContext, presenterInteractorDelegate, useCase, mapper)

    @Provides
    @DetailsScope
    fun providesMovieDetailsCreditsPresenterInteractor(presenterInteractorDelegate: PresenterInteractorDelegate,
                                                       imageConfigurationInteractor: ImageConfigurationInteractor): MovieDetailsCreditsPresenterInteractor
            = MovieDetailsCreditsPresenterInteractorImpl(presenterInteractorDelegate, imageConfigurationInteractor)

    @Provides
    @DetailsScope
    fun providesImageConfigurationPresenterDelegate(): ImageConfigurationInteractor
            = ImageConfigurationInteractorImpl()

}