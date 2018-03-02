package com.jpp.moviespreview.app.ui.sections.detail.di

import com.jpp.moviespreview.app.di.activity.ActivityComponent
import com.jpp.moviespreview.app.di.activity.ActivityComponentBuilder
import com.jpp.moviespreview.app.di.activity.ActivityModule
import com.jpp.moviespreview.app.di.activity.ActivityScope
import com.jpp.moviespreview.app.ui.MoviesContextHandler
import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailActivity
import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailImagesPresenter
import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailImagesPresenterImpl
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

/**
 * Provides dependency injection for the [MovieDetailActivity]
 *
 * Created by jpp on 3/2/18.
 */
@ActivityScope
@Subcomponent(modules = [(MovieDetailActivityComponent.MovieDetailActivityModule::class)])
interface MovieDetailActivityComponent : ActivityComponent<MovieDetailActivity> {


    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<MovieDetailActivityModule, MovieDetailActivityComponent>


    @Module
    class MovieDetailActivityModule internal constructor(activity: MovieDetailActivity) : ActivityModule<MovieDetailActivity>(activity) {

        @Provides
        @ActivityScope
        fun providesMovieDetailImagesPresenter(moviesContextHandler: MoviesContextHandler): MovieDetailImagesPresenter =
                MovieDetailImagesPresenterImpl(moviesContextHandler)
    }
}