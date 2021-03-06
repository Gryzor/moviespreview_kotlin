package com.jpp.moviespreview.app

import com.jpp.moviespreview.app.data.EspressoDataModule
import com.jpp.moviespreview.app.domain.EspressoDomainModule
import com.jpp.moviespreview.app.ui.EspressoUiModule
import com.jpp.moviespreview.app.ui.sections.detail.body.MovieDetailsFragmentEspressoTests
import com.jpp.moviespreview.app.ui.sections.detail.credits.MovieCreditsFragmentEspressoTests
import com.jpp.moviespreview.app.ui.sections.main.playing.PlayingMoviesFragmentEspressoTest
import com.jpp.moviespreview.app.ui.sections.search.MultiSearchActivityEspressoTest
import com.jpp.moviespreview.app.ui.sections.splash.SplashActivityEspressoTest
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
    fun inject(movieCreditsFragmentEspressoTests: MovieCreditsFragmentEspressoTests)
    fun inject(multiSearchActivityEspressoTest: MultiSearchActivityEspressoTest)
}