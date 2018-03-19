package com.jpp.moviespreview.app.domain

import com.jpp.moviespreview.app.data.cache.MoviesCache
import com.jpp.moviespreview.app.data.cache.MoviesConfigurationCache
import com.jpp.moviespreview.app.data.cache.MoviesGenreCache
import com.jpp.moviespreview.app.data.cache.file.AssetLoader
import com.jpp.moviespreview.app.data.server.MoviesPreviewApiWrapper
import com.jpp.moviespreview.app.domain.configuration.ConfigurationDataMapper
import com.jpp.moviespreview.app.domain.configuration.RetrieveConfigurationCommand
import com.jpp.moviespreview.app.domain.configuration.RetrieveConfigurationUseCase
import com.jpp.moviespreview.app.domain.genre.GenreDataMapper
import com.jpp.moviespreview.app.domain.genre.RetrieveGenresCommand
import com.jpp.moviespreview.app.domain.genre.RetrieveGenresUseCase
import com.jpp.moviespreview.app.domain.licenses.LicensesDataMapper
import com.jpp.moviespreview.app.domain.licenses.RetrieveLicensesUseCase
import com.jpp.moviespreview.app.domain.movie.MovieDataMapper
import com.jpp.moviespreview.app.domain.movie.RetrieveMoviesInTheaterCommand
import com.jpp.moviespreview.app.domain.movie.RetrieveMoviesInTheaterUseCase
import com.jpp.moviespreview.app.domain.movie.credits.CreditsDataMapper
import com.jpp.moviespreview.app.domain.movie.credits.RetrieveMovieCreditsCommand
import com.jpp.moviespreview.app.domain.movie.credits.RetrieveMovieCreditsUseCase
import com.jpp.moviespreview.app.domain.search.MultiSearchCommand
import com.jpp.moviespreview.app.domain.search.MultiSearchDataMapper
import com.jpp.moviespreview.app.domain.search.MultiSearchUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * DI module to provide domain dependencies
 *
 * Created by jpp on 12/26/17.
 */
@Module
class DomainModule {

    @Provides
    @Singleton
    fun provideRetrieveMovieGenresUseCase(apiInstance: MoviesPreviewApiWrapper,
                                          genresCache: MoviesGenreCache): UseCase<Any, List<Genre>> = RetrieveGenresUseCase(GenreDataMapper(), apiInstance, genresCache)

    @Provides
    @Singleton
    fun provideRetrieveConfigurationUseCase(apiInstance: MoviesPreviewApiWrapper,
                                            configurationCache: MoviesConfigurationCache): UseCase<Any, MoviesConfiguration> = RetrieveConfigurationUseCase(ConfigurationDataMapper(), apiInstance, configurationCache)


    @Provides
    @Singleton
    fun providesRetrieveMoviesInTheaterUseCase(api: MoviesPreviewApiWrapper,
                                               moviesCache: MoviesCache,
                                               movieDataMapper: MovieDataMapper): UseCase<PageParam, MoviePage> = RetrieveMoviesInTheaterUseCase(movieDataMapper, api, moviesCache)


    @Provides
    @Singleton
    fun providesRetrieveMoviesCreditUseCase(api: MoviesPreviewApiWrapper,
                                            moviesCache: MoviesCache): UseCase<Movie, MovieCredits> = RetrieveMovieCreditsUseCase(CreditsDataMapper(), api, moviesCache)


    @Provides
    @Singleton
    fun providesMultiSearchUseCase(api: MoviesPreviewApiWrapper,
                                   movieDataMapper: MovieDataMapper): UseCase<MultiSearchParam, MultiSearchPage> = MultiSearchUseCase(MultiSearchDataMapper(movieDataMapper), api)

    @Provides
    @Singleton
    fun providesRetrieveLicencesUseCase(assetLoader: AssetLoader): UseCase<Any, Licenses> = RetrieveLicensesUseCase(assetLoader, LicensesDataMapper())

    @Provides
    @Singleton
    fun providesMovieDataMapper() = MovieDataMapper()


    @Provides
    @Singleton
    fun providesRetrieveConfigurationCommand(apiInstance: MoviesPreviewApiWrapper,
                                             configurationCache: MoviesConfigurationCache): Command<Any, MoviesConfiguration> = RetrieveConfigurationCommand(ConfigurationDataMapper(), apiInstance, configurationCache)

    @Provides
    @Singleton
    fun providesRetrieveGenresCommand(apiInstance: MoviesPreviewApiWrapper,
                                      genresCache: MoviesGenreCache): Command<Any, List<Genre>> = RetrieveGenresCommand(GenreDataMapper(), apiInstance, genresCache)


    @Provides
    @Singleton
    fun providesRetrieveMoviesInTheaterCommand(mapper: MovieDataMapper,
                                               api: MoviesPreviewApiWrapper,
                                               moviesCache: MoviesCache)
            : Command<PageParam, MoviePage> = RetrieveMoviesInTheaterCommand(mapper, api, moviesCache)

    @Provides
    @Singleton
    fun providesRetrieveMovieCreditsCommand(api: MoviesPreviewApiWrapper,
                                            moviesCache: MoviesCache)
            : Command<Movie, MovieCredits> = RetrieveMovieCreditsCommand(CreditsDataMapper(), api, moviesCache)


    @Provides
    @Singleton
    fun providesMultiSearchCommand(movieDataMapper: MovieDataMapper,
                                   api: MoviesPreviewApiWrapper)
            : Command<MultiSearchParam, MultiSearchPage> = MultiSearchCommand(MultiSearchDataMapper(movieDataMapper), api)

}