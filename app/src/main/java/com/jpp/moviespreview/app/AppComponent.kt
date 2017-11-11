package com.jpp.moviespreview.app

import com.jpp.moviespreview.app.data.DataModule
import com.jpp.moviespreview.app.ui.UiModule
import com.jpp.moviespreview.app.ui.detail.di.MovieDetailsComponent
import com.jpp.moviespreview.app.ui.detail.di.MovieDetailsModule
import com.jpp.moviespreview.app.ui.main.playing.di.PlayingMoviesComponent
import com.jpp.moviespreview.app.ui.main.playing.di.PlayingMoviesModule
import com.jpp.moviespreview.app.ui.splash.di.SplashComponent
import com.jpp.moviespreview.app.ui.splash.di.SplashModule
import dagger.Component
import javax.inject.Singleton

/**
 * Application scope component
 *
 * Created by jpp on 10/4/17.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, DataModule::class, UiModule::class))
interface AppComponent {
    fun plus(splashModule: SplashModule): SplashComponent
    fun plus(playingMovies: PlayingMoviesModule): PlayingMoviesComponent
    fun pluse(movieDetails: MovieDetailsModule): MovieDetailsComponent
}