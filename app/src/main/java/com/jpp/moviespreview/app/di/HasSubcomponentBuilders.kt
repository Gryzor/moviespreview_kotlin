package com.jpp.moviespreview.app.di

import android.app.Activity
import android.support.v4.app.Fragment
import com.jpp.moviespreview.app.di.activity.ActivityComponentBuilder
import com.jpp.moviespreview.app.di.fragment.FragmentComponentBuilder

/**
 * Interface definition for a subject that provides [ActivityComponentBuilder] for the provided
 * class.
 *
 * Created by jpp on 2/14/18.
 */
interface HasSubcomponentBuilders {
    fun getActivityComponentBuilder(activityClass: Class<out Activity>): ActivityComponentBuilder<*, *>
    fun getFragmentComponentBuilder(fragmentClass: Class<out Fragment>): FragmentComponentBuilder<*, *>
}