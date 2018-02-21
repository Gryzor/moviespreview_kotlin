package com.jpp.moviespreview.app.di.activity

import android.app.Activity

/**
 * Interface definition for a subject that provides [ActivityComponentBuilder] for the provided
 * class.
 *
 * Created by jpp on 2/14/18.
 */
interface HasActivitySubcomponentBuilders {
    fun getActivityComponentBuilder(activityClass: Class<out Activity>): ActivityComponentBuilder<*, *>
}