package com.jpp.moviespreview.app.di.activity

import android.app.Activity
import dagger.Module
import dagger.Provides

/**
 * Dagger Module base class to provide Activity dependencies.
 *
 * Created by jpp on 2/14/18.
 */
@Module
abstract class ActivityModule<out T : Activity>(protected val activity: T) {


    @Provides
    @ActivityScope
    fun provideActivity(): T = activity

}