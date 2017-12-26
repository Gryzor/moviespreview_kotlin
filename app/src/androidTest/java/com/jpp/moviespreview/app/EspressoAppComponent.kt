package com.jpp.moviespreview.app

import com.jpp.moviespreview.app.data.EspressoDataModule
import com.jpp.moviespreview.app.domain.EspressoDomainModule
import com.jpp.moviespreview.app.ui.EspressoUiModule
import com.jpp.moviespreview.app.ui.detail.body.MovieDetailsFragmentEspressoTests
import com.jpp.moviespreview.app.ui.main.playing.PlayingMoviesFragmentEspressoTest
import com.jpp.moviespreview.app.ui.splash.SplashActivityEspressoTest
import dagger.Component
import javax.inject.Singleton

/**
 * Created by jpp on 10/13/17.
 */
@Singleton
@Component(modules = arrayOf(EspressoAppModule::class, EspressoUiModule::class, EspressoDataModule::class, EspressoDomainModule::class))
interface EspressoAppComponent : AppComponent {

    fun inject(splashActivityEspressoTest: SplashActivityEspressoTest)
    fun inject(playingMoviesFragmentEspressoTest: PlayingMoviesFragmentEspressoTest)
    fun inject(movieDetailsFragmentEspressoTests: MovieDetailsFragmentEspressoTests)
}