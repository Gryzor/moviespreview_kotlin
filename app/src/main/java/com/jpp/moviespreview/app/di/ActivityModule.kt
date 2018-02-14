package com.jpp.moviespreview.app.di

import android.app.Activity
import dagger.Module
import dagger.Provides

/**
 * Created by jpp on 2/14/18.
 */
@Module abstract class ActivityModule<out T : Activity>(protected val activity: T) {


    @Provides
    @ActivityScope
    fun provideActivity(): T {
        return activity
    }

}