package com.jpp.moviespreview.app.di

import com.jpp.moviespreview.app.ui.sections.splash.SplashActivity
import com.jpp.moviespreview.app.ui.sections.splash.di.SplashActivityComponent
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by jpp on 2/14/18.
 */
@Module(subcomponents = arrayOf(SplashActivityComponent::class))
abstract class ActivityBindingModule {

    @Binds
    @IntoMap
    @ActivityKey(SplashActivity::class)
    abstract fun splashActivityComponentBuilder(impl: SplashActivityComponent.Builder): ActivityComponentBuilder<*, *>

}