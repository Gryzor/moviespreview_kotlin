package com.jpp.moviespreview.app

import com.jpp.moviespreview.app.data.DataModule
import com.jpp.moviespreview.app.di.activity.ActivityBindingModule
import com.jpp.moviespreview.app.di.fragment.FragmentBindingModule
import com.jpp.moviespreview.app.domain.DomainModule
import com.jpp.moviespreview.app.ui.UiModule
import com.jpp.moviespreview.app.ui.sections.about.di.AboutComponent
import com.jpp.moviespreview.app.ui.sections.about.di.AboutModule
import com.jpp.moviespreview.app.ui.sections.detail.di.MovieDetailsComponent
import com.jpp.moviespreview.app.ui.sections.detail.di.MovieDetailsModule
import com.jpp.moviespreview.app.ui.sections.search.di.MultiSearchComponent
import com.jpp.moviespreview.app.ui.sections.search.di.MultiSearchModule
import dagger.Component
import javax.inject.Singleton

/**
 * Application scope component
 *
 * Created by jpp on 10/4/17.
 */
@Singleton
@Component(modules = [
    (ActivityBindingModule::class),
    (FragmentBindingModule::class),
    (AppModule::class),
    (DataModule::class),
    (DomainModule::class),
    (UiModule::class)
])
interface AppComponent {

    fun inject(application: MoviesPreviewApp): MoviesPreviewApp

    fun plus(multiSearch: MultiSearchModule): MultiSearchComponent
    fun plus(about: AboutModule): AboutComponent
}