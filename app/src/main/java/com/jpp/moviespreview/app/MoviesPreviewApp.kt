package com.jpp.moviespreview.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.StrictMode
import com.jpp.moviespreview.BuildConfig
import com.jpp.moviespreview.app.data.DataModule
import com.jpp.moviespreview.app.di.ActivityComponentBuilder
import com.jpp.moviespreview.app.di.HasActivitySubcomponentBuilders
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
import javax.inject.Inject
import javax.inject.Provider

/**
 * Application class that injects the initial application scope graph
 *
 * Created by jpp on 10/4/17.
 */
open class MoviesPreviewApp : Application(), HasActivitySubcomponentBuilders {

    @Inject lateinit var activityComponentBuilders: Map<Class<out Activity>, @JvmSuppressWildcards Provider<ActivityComponentBuilder<*, *>>>

    lateinit var appComponent: AppComponent


    override fun onCreate() {
        if (BuildConfig.DEBUG) {
            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .build())
        }


        super.onCreate()


        appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .dataModule(DataModule())
                .domainModule(DomainModule())
                .uiModule(UiModule())
                .build()
        appComponent.inject(this)
    }


    override fun getActivityComponentBuilder(activityClass: Class<out Activity>): ActivityComponentBuilder<*, *> {
        return activityComponentBuilders[activityClass]!!.get()
    }


    companion object {

        operator fun get(context: Context): HasActivitySubcomponentBuilders {
            return context.applicationContext as HasActivitySubcomponentBuilders
        }
    }


    open fun playingMoviesComponent(): PlayingMoviesComponent = appComponent.plus(PlayingMoviesModule())

    open fun movieDetailsComponent(): MovieDetailsComponent = appComponent.plus(MovieDetailsModule())

    open fun multiSearchComponent(): MultiSearchComponent = appComponent.plus(MultiSearchModule())

    open fun aboutComponent(): AboutComponent = appComponent.plus(AboutModule())

}