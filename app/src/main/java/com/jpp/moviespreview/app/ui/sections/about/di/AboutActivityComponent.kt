package com.jpp.moviespreview.app.ui.sections.about.di

import android.content.Context
import com.jpp.moviespreview.app.di.activity.ActivityComponent
import com.jpp.moviespreview.app.di.activity.ActivityComponentBuilder
import com.jpp.moviespreview.app.di.activity.ActivityModule
import com.jpp.moviespreview.app.di.activity.ActivityScope
import com.jpp.moviespreview.app.ui.sections.about.*
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

/**
 * [ActivityComponent] for the about section.
 *
 *
 *
 * Created by jpp on 3/27/18.
 */
@ActivityScope
@Subcomponent(modules = [(AboutActivityComponent.AboutActivityModule::class)])
interface AboutActivityComponent : ActivityComponent<AboutActivity> {


    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<AboutActivityModule, AboutActivityComponent>


    @Module
    class AboutActivityModule internal constructor(activity: AboutActivity) : ActivityModule<AboutActivity>(activity) {
        @ActivityScope
        @Provides
        fun providesAboutInteractor(context: Context): AboutInteractor = AboutInteractorImpl(context)


        @ActivityScope
        @Provides
        fun providesAboutPresenter(aboutInteractor: AboutInteractor): AboutPresenter = AboutPresenterImpl(aboutInteractor)
    }

}