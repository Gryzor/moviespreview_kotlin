package com.jpp.moviespreview.app.ui.sections.detail.di

import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailActivity
import com.jpp.moviespreview.app.ui.sections.detail.body.MovieDetailsFragment
import com.jpp.moviespreview.app.ui.sections.detail.credits.MovieCreditsFragment
import dagger.Subcomponent

/**
 * Movie details sub-component
 *
 * Created by jpp on 11/11/17.
 */
@DetailsScope
@Subcomponent(modules = arrayOf(MovieDetailsModule::class))
interface MovieDetailsComponent {

    fun inject(movieDetailsActivity: MovieDetailActivity)
    fun inject(movieDetailsBodyFragment: MovieDetailsFragment)
    fun inject(movieCreditsFragment: MovieCreditsFragment)
}