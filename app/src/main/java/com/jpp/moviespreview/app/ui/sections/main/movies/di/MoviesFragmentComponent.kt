package com.jpp.moviespreview.app.ui.sections.main.movies.di

import com.jpp.moviespreview.app.di.fragment.FragmentComponent
import com.jpp.moviespreview.app.di.fragment.FragmentComponentBuilder
import com.jpp.moviespreview.app.di.fragment.FragmentModule
import com.jpp.moviespreview.app.di.fragment.FragmentScope
import com.jpp.moviespreview.app.domain.Command
import com.jpp.moviespreview.app.domain.MoviePage
import com.jpp.moviespreview.app.domain.PageParam
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.FlowResolver
import com.jpp.moviespreview.app.ui.FlowResolverImpl
import com.jpp.moviespreview.app.ui.MoviesContextHandler
import com.jpp.moviespreview.app.ui.interactors.*
import com.jpp.moviespreview.app.ui.sections.main.movies.*
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

/**
 * [FragmentComponent] for the movies module.
 *
 *
 * Created by jpp on 2/21/18.
 */
@FragmentScope
@Subcomponent(modules = [(MoviesFragmentComponent.MoviesFragmentModule::class)])
interface MoviesFragmentComponent : FragmentComponent<MoviesFragment> {

    @Subcomponent.Builder
    interface Builder : FragmentComponentBuilder<MoviesFragmentModule, MoviesFragmentComponent>

    @Module
    class MoviesFragmentModule internal constructor(fragment: MoviesFragment) : FragmentModule<MoviesFragment>(fragment) {

        @Provides
        @FragmentScope
        fun providesMoviesPresenter(moviesContextHandler: MoviesContextHandler,
                                    backgroundExecutor: BackgroundExecutor,
                                    interactor: MoviesPresenterInteractor,
                                    paginationController: PaginationController,
                                    imageConfigManager: ImageConfigurationManager,
                                    flowResolver: FlowResolver): MoviesPresenter =
                MoviesPresenterImpl(moviesContextHandler, backgroundExecutor, interactor, paginationController, imageConfigManager, flowResolver)

        @Provides
        @FragmentScope
        fun providesImageConfigurationManager(): ImageConfigurationManager = ImageConfigurationManagerImpl()


        @Provides
        @FragmentScope
        fun providesMoviesPresenterInteractor(mapper: DomainToUiDataMapper,
                                              connectivityInteractor: ConnectivityInteractor,
                                              retrieveMoviePageCommand: Command<@JvmSuppressWildcards PageParam, MoviePage>): MoviesPresenterInteractor =
                MoviesPresenterInteractorImpl(mapper, connectivityInteractor, retrieveMoviePageCommand)


        /*
         * IMPORTANT: this can be injected this way (using the fragment's activity) because
         * MoviesFragment is injecting the dependencies in onActivityCreated.
         * If this changes, the provider method will have to be updated.
         */
        @Provides
        @FragmentScope
        fun providesFlowResolver(): FlowResolver = FlowResolverImpl(fragment.activity)
    }
}