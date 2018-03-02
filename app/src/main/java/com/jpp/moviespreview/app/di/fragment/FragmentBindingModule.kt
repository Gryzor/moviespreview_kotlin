package com.jpp.moviespreview.app.di.fragment

import com.jpp.moviespreview.app.ui.sections.detail.body.MovieDetailsFragment
import com.jpp.moviespreview.app.ui.sections.detail.body.di.MovieDetailsFragmentComponent
import com.jpp.moviespreview.app.ui.sections.main.movies.MoviesFragment
import com.jpp.moviespreview.app.ui.sections.main.movies.di.MoviesFragmentComponent
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Dagger Module that constructs the [FragmentComponentBuilder]s for all the Fragments in the project.
 * We use multibinding to have a map of sub-components builders to be able to get the intended
 * builder for each Activity class.
 *
 * Created by jpp on 2/21/18.
 */
@Module(subcomponents = [(MoviesFragmentComponent::class),
    (MovieDetailsFragmentComponent::class)])
abstract class FragmentBindingModule {

    @Binds
    @IntoMap
    @FragmentKey(MoviesFragment::class)
    abstract fun moviesFragmentComponentBuilder(impl: MoviesFragmentComponent.Builder): FragmentComponentBuilder<*, *>

    @Binds
    @IntoMap
    @FragmentKey(MovieDetailsFragment::class)
    abstract fun movieDetailsFragmentComponentBuilder(impl: MovieDetailsFragmentComponent.Builder): FragmentComponentBuilder<*, *>

}