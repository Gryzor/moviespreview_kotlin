package com.jpp.moviespreview.app.domain

import com.jpp.moviespreview.app.mock
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 *  Provides the DomainModule implementation for espresso tests
 *
 * Created by jpp on 12/26/17.
 */
@Module
class EspressoDomainModule {

    @Provides
    @Singleton
    fun provideRetrieveMovieGenresUseCase(): UseCase<Any, List<Genre>> = mock()

    @Provides
    @Singleton
    fun provideRetrieveConfigurationUseCase(): UseCase<Any, MoviesConfiguration> = mock()


    @Provides
    @Singleton
    fun providesRetrieveMoviesInTheaterUseCase(): UseCase<PageParam, MoviePage> = mock()


    @Provides
    @Singleton
    fun providesRetrieveMoviesCreditUseCase(): UseCase<Movie, MovieCredits> = mock()

    @Provides
    @Singleton
    fun providesMultiSearchUseCase(): UseCase<MultiSearchParam, MultiSearchPage> = mock()

    @Provides
    @Singleton
    fun providesRetrieveLicencesUseCase(): UseCase<Any, Licenses> = mock()

    @Provides
    @Singleton
    fun providesRetrieveConfigurationCommand(): Command<Any, MoviesConfiguration> = mock()

    @Provides
    @Singleton
    fun providesRetrieveGenresCommand(): Command<Any, List<Genre>> = mock()

    @Provides
    @Singleton
    fun providesRetrieveMoviesInTheaterCommand(): Command<PageParam, MoviePage> = mock()

    @Provides
    @Singleton
    fun providesRetrieveMovieCreditsCommand(): Command<Movie, MovieCredits> = mock()

    @Provides
    @Singleton
    fun providesMultiSearchCommand(): Command<MultiSearchParam, MultiSearchPage> = mock()
}