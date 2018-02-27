package com.jpp.moviespreview.app.di.fragment

import android.support.v4.app.Fragment
import dagger.Module
import dagger.Provides

/**
 * Dagger module base class to provide Fragment dependencies.
 *
 * Created by jpp on 2/21/18.
 */
@Module
abstract class FragmentModule<out T : Fragment>(protected val fragment: T) {

    @Provides
    @FragmentScope
    fun provideFragment(): T = fragment

}