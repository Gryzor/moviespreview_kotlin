package com.jpp.moviespreview.app.ui.sections.detail.body.di

import com.jpp.moviespreview.app.di.fragment.FragmentComponent
import com.jpp.moviespreview.app.di.fragment.FragmentComponentBuilder
import com.jpp.moviespreview.app.di.fragment.FragmentModule
import com.jpp.moviespreview.app.di.fragment.FragmentScope
import com.jpp.moviespreview.app.ui.ApplicationMoviesContext
import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailPresenter
import com.jpp.moviespreview.app.ui.sections.detail.body.MovieDetailPresenterImpl
import com.jpp.moviespreview.app.ui.sections.detail.body.MovieDetailsFragment
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

/**
 * Provides dependencies for [MovieDetailsFragment].
 *
 * Created by jpp on 3/2/18.
 */
@FragmentScope
@Subcomponent(modules = [(MovieDetailsFragmentComponent.MovieDetailsFragmentModule::class)])
interface MovieDetailsFragmentComponent : FragmentComponent<MovieDetailsFragment> {

    @Subcomponent.Builder
    interface Builder : FragmentComponentBuilder<MovieDetailsFragmentModule, MovieDetailsFragmentComponent>


    @Module
    class MovieDetailsFragmentModule internal constructor(fragment: MovieDetailsFragment) : FragmentModule<MovieDetailsFragment>(fragment) {

        @Provides
        @FragmentScope
        fun providesMovieDetailsPresenter(moviesContext: ApplicationMoviesContext): MovieDetailPresenter = MovieDetailPresenterImpl(moviesContext)

    }

}