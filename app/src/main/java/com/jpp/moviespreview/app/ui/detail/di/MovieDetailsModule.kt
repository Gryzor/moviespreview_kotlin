package com.jpp.moviespreview.app.ui.detail.di

import com.jpp.moviespreview.app.domain.Movie
import com.jpp.moviespreview.app.domain.MovieCredits
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.detail.MovieDetailCreditsPresenter
import com.jpp.moviespreview.app.ui.detail.MovieDetailImagesPresenter
import com.jpp.moviespreview.app.ui.detail.MovieDetailImagesPresenterImpl
import com.jpp.moviespreview.app.ui.detail.MovieDetailPresenter
import com.jpp.moviespreview.app.ui.detail.body.MovieDetailPresenterImpl
import com.jpp.moviespreview.app.ui.detail.credits.MovieDetailCreditsPresenterImpl
import com.jpp.moviespreview.app.ui.detail.credits.MovieDetailsCreditsPresenterInteractor
import com.jpp.moviespreview.app.ui.detail.credits.MovieDetailsCreditsPresenterInteractorImpl
import com.jpp.moviespreview.app.ui.interactors.PresenterInteractorDelegate
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
    fun providesMovieDetailImagesPresenter(moviesContext: MoviesContext): MovieDetailImagesPresenter =
            MovieDetailImagesPresenterImpl(moviesContext)


    @Provides
    @DetailsScope
    fun providesMovieDetailsPresenter(moviesContext: MoviesContext): MovieDetailPresenter
            = MovieDetailPresenterImpl(moviesContext)

    @Provides
    @DetailsScope
    fun providesMovieDetailsCreditsPresenter(moviesContext: MoviesContext,
                                             presenterInteractorDelegate: MovieDetailsCreditsPresenterInteractor,
                                             useCase: UseCase<Movie, MovieCredits>,
                                             mapper: DomainToUiDataMapper): MovieDetailCreditsPresenter
            = MovieDetailCreditsPresenterImpl(moviesContext, presenterInteractorDelegate, useCase, mapper)

    @Provides
    @DetailsScope
    fun providesMovieDetailsCreditsPresenterInteractor(presenterInteractorDelegate: PresenterInteractorDelegate): MovieDetailsCreditsPresenterInteractor
            = MovieDetailsCreditsPresenterInteractorImpl(presenterInteractorDelegate)

}