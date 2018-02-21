package com.jpp.moviespreview.app.ui.sections.splash.di

import com.jpp.moviespreview.app.di.activity.ActivityComponent
import com.jpp.moviespreview.app.di.activity.ActivityComponentBuilder
import com.jpp.moviespreview.app.di.activity.ActivityModule
import com.jpp.moviespreview.app.di.activity.ActivityScope
import com.jpp.moviespreview.app.domain.Command
import com.jpp.moviespreview.app.domain.Genre
import com.jpp.moviespreview.app.domain.MoviesConfiguration
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.ApplicationMoviesContext
import com.jpp.moviespreview.app.ui.interactors.BackgroundExecutor
import com.jpp.moviespreview.app.ui.interactors.ConnectivityInteractor
import com.jpp.moviespreview.app.ui.sections.splash.*
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

/**
 * [ActivityComponent] for the splash module.
 *
 *
 * Created by jpp on 2/14/18.
 */
@ActivityScope
@Subcomponent(modules = arrayOf(SplashActivityComponent.SplashActivityModule::class))
interface SplashActivityComponent : ActivityComponent<SplashActivity> {

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<SplashActivityModule, SplashActivityComponent>

    @Module
    class SplashActivityModule internal constructor(activity: SplashActivity) : ActivityModule<SplashActivity>(activity) {

        @Provides
        @ActivityScope
        fun providesSplashPresenter(moviesContext: ApplicationMoviesContext,
                                    backgroundExecutor: BackgroundExecutor,
                                    interactor: SplashPresenterInteractor): SplashPresenter
                = SplashPresenterImpl(moviesContext, backgroundExecutor, interactor)

        @Provides
        @ActivityScope
        fun providesSplashPresenterInteractor(mapper: DomainToUiDataMapper,
                                              connectivityInteractor: ConnectivityInteractor,
                                              retrieveConfigurationCommand: Command<Any, MoviesConfiguration>,
                                              retrieveGenresCommand: Command<Any, List<Genre>>): SplashPresenterInteractor
                = SplashPresenterInteractorImpl(mapper, connectivityInteractor, retrieveConfigurationCommand, retrieveGenresCommand)
    }

}