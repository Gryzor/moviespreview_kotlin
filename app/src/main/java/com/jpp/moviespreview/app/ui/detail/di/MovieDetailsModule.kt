package com.jpp.moviespreview.app.ui.detail.di

import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.detail.MovieDetailImagesPresenter
import com.jpp.moviespreview.app.ui.detail.MovieDetailImagesPresenterImpl
import com.jpp.moviespreview.app.ui.detail.MovieDetailPresenter
import com.jpp.moviespreview.app.ui.detail.body.MovieDetailPresenterImpl
import com.jpp.moviespreview.app.ui.interactors.BackgroundInteractor
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractor
import com.jpp.moviespreview.app.ui.interactors.PresenterInteractorDelegate
import com.jpp.moviespreview.app.ui.interactors.PresenterInteractorDelegateImpl
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
    fun provideMovieDetailsInteractorDelegate(backgroundInteractor: BackgroundInteractor,
                                              connectivityInteractor: ConnectivityInteractor): PresenterInteractorDelegate
            = PresenterInteractorDelegateImpl(backgroundInteractor, connectivityInteractor)
}