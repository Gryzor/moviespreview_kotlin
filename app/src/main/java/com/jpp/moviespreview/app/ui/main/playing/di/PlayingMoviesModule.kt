package com.jpp.moviespreview.app.ui.main.playing.di

import com.jpp.moviespreview.app.ui.main.di.MainScreenScope
import com.jpp.moviespreview.app.ui.main.playing.PlayingMoviesPresenter
import com.jpp.moviespreview.app.ui.main.playing.PlayingMoviesPresenterImpl
import dagger.Module
import dagger.Provides

/**
 * Provides dependencies for the playing movies in theater section
 *
 * Created by jpp on 10/23/17.
 */
@Module
class PlayingMoviesModule {


    @Provides
    @MainScreenScope
    fun providesPlayingMoviesPresenter(): PlayingMoviesPresenter = PlayingMoviesPresenterImpl()

}