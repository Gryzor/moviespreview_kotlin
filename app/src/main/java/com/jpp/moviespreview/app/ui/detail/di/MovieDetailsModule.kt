package com.jpp.moviespreview.app.ui.detail.di

import com.jpp.moviespreview.app.data.cache.MoviesCache
import com.jpp.moviespreview.app.data.server.MoviesPreviewApiWrapper
import com.jpp.moviespreview.app.domain.Movie
import com.jpp.moviespreview.app.domain.MovieCredits
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.domain.movie.credits.CreditsDataMapper
import com.jpp.moviespreview.app.domain.movie.credits.RetrieveMovieCreditsUseCase
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.detail.MovieDetailPresenter
import com.jpp.moviespreview.app.ui.detail.MovieDetailPresenterImpl
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
    fun providesMovieDetailsPresenter(moviesContext: MoviesContext,
                                      presenterInteractorDelegate: PresenterInteractorDelegate,
                                      mapper: DomainToUiDataMapper,
                                      usecase: UseCase<Movie, MovieCredits>) : MovieDetailPresenter
            = MovieDetailPresenterImpl(moviesContext, presenterInteractorDelegate, mapper, usecase)


    @Provides
    @DetailsScope
    fun providesRetrieveMoviesCreditUseCase(api: MoviesPreviewApiWrapper, moviesCache: MoviesCache): UseCase<Movie, MovieCredits>
            = RetrieveMovieCreditsUseCase(CreditsDataMapper(), api, moviesCache)


    @Provides
    @DetailsScope
    fun provideMovieDetailsInteractorDelegate(backgroundInteractor: BackgroundInteractor,
                                              connectivityInteractor: ConnectivityInteractor): PresenterInteractorDelegate
            = PresenterInteractorDelegateImpl(backgroundInteractor, connectivityInteractor)
}