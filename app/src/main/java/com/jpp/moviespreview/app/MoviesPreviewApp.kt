package com.jpp.moviespreview.app

import android.app.Application
import com.jpp.moviespreview.app.data.DataModule
import com.jpp.moviespreview.app.domain.DomainModule
import com.jpp.moviespreview.app.ui.UiModule
import com.jpp.moviespreview.app.ui.sections.about.di.AboutComponent
import com.jpp.moviespreview.app.ui.sections.about.di.AboutModule
import com.jpp.moviespreview.app.ui.sections.detail.di.MovieDetailsComponent
import com.jpp.moviespreview.app.ui.sections.detail.di.MovieDetailsModule
import com.jpp.moviespreview.app.ui.sections.main.playing.di.PlayingMoviesComponent
import com.jpp.moviespreview.app.ui.sections.main.playing.di.PlayingMoviesModule
import com.jpp.moviespreview.app.ui.sections.search.di.MultiSearchComponent
import com.jpp.moviespreview.app.ui.sections.search.di.MultiSearchModule
import com.jpp.moviespreview.app.ui.sections.splash.di.SplashComponent
import com.jpp.moviespreview.app.ui.sections.splash.di.SplashModule

/**
 * Application class that injects the initial application scope graph
 *
 * Created by jpp on 10/4/17.
 */
open class MoviesPreviewApp : Application() {

    lateinit var appComponent: AppComponent


    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .dataModule(DataModule())
                .domainModule(DomainModule())
                .uiModule(UiModule())
                .build()
    }

    open fun splashComponent(): SplashComponent = appComponent.plus(SplashModule())

    open fun playingMoviesComponent(): PlayingMoviesComponent = appComponent.plus(PlayingMoviesModule())

    open fun movieDetailsComponent(): MovieDetailsComponent = appComponent.plus(MovieDetailsModule())

    open fun multiSearchComponent(): MultiSearchComponent = appComponent.plus(MultiSearchModule())

    open fun aboutComponent(): AboutComponent = appComponent.plus(AboutModule())

}