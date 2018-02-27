package com.jpp.moviespreview.app.di.activity

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
@Module(subcomponents = [(SplashActivityComponent::class)])
abstract class ActivityBindingModule {

    @Binds
    @IntoMap
    @ActivityKey(SplashActivity::class)
    abstract fun splashActivityComponentBuilder(impl: SplashActivityComponent.Builder): ActivityComponentBuilder<*, *>

}