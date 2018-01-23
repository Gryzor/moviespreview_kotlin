package com.jpp.moviespreview.app.ui.sections.main.playing.di

import com.jpp.moviespreview.app.ui.sections.main.di.MainScreenScope
import com.jpp.moviespreview.app.ui.sections.main.playing.PlayingMoviesFragment
import dagger.Subcomponent

/**
 * Sub-component for the playing movies in theater section.
 *
 * Created by jpp on 10/23/17.
 */
@MainScreenScope
@Subcomponent(modules = arrayOf(PlayingMoviesModule::class))
interface PlayingMoviesComponent {

    fun inject(fragment: PlayingMoviesFragment)

}