package com.jpp.moviespreview.app.ui.sections.main.movies.di

import com.jpp.moviespreview.app.di.fragment.FragmentComponent
import com.jpp.moviespreview.app.di.fragment.FragmentComponentBuilder
import com.jpp.moviespreview.app.di.fragment.FragmentModule
import com.jpp.moviespreview.app.di.fragment.FragmentScope
import com.jpp.moviespreview.app.ui.sections.main.movies.MoviesFragment
import com.jpp.moviespreview.app.ui.sections.main.movies.MoviesPresenter
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

/**
 * [FragmentComponent] for the movies module.
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
        fun providesMoviesPresenter(): MoviesPresenter = TODO("Implement me")

    }

}