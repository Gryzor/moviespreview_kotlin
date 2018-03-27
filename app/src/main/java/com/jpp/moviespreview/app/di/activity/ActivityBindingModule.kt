package com.jpp.moviespreview.app.di.activity

import com.jpp.moviespreview.app.ui.sections.about.licenses.LicensesActivity
import com.jpp.moviespreview.app.ui.sections.about.licenses.di.LicensesActivityComponent
import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailActivity
import com.jpp.moviespreview.app.ui.sections.detail.di.MovieDetailActivityComponent
import com.jpp.moviespreview.app.ui.sections.search.MultiSearchActivity
import com.jpp.moviespreview.app.ui.sections.search.di.MultiSearchActivityComponent
import com.jpp.moviespreview.app.ui.sections.splash.SplashActivity
import com.jpp.moviespreview.app.ui.sections.splash.di.SplashActivityComponent
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Dagger Module that constructs the [ActivityComponentBuilder] for all the activities in the project.
 * We use multibinding to have a map of sub-components builders to be able to get the intended
 * builder for each Activity class.
 *
 * Created by jpp on 2/14/18.
 */
@Module(subcomponents = [(SplashActivityComponent::class),
    (MovieDetailActivityComponent::class),
    (MultiSearchActivityComponent::class),
    (LicensesActivityComponent::class)])
abstract class ActivityBindingModule {

    @Binds
    @IntoMap
    @ActivityKey(SplashActivity::class)
    abstract fun splashActivityComponentBuilder(impl: SplashActivityComponent.Builder): ActivityComponentBuilder<*, *>

    @Binds
    @IntoMap
    @ActivityKey(MovieDetailActivity::class)
    abstract fun movieDetailActivityComponentBuilder(impl: MovieDetailActivityComponent.Builder): ActivityComponentBuilder<*, *>

    @Binds
    @IntoMap
    @ActivityKey(MultiSearchActivity::class)
    abstract fun multiSearchActivityComponentBuilder(impl: MultiSearchActivityComponent.Builder): ActivityComponentBuilder<*, *>

    @Binds
    @IntoMap
    @ActivityKey(LicensesActivity::class)
    abstract fun licencesActivityComponentBuilder(impl: LicensesActivityComponent.Builder): ActivityComponentBuilder<*, *>

}