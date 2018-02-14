package com.jpp.moviespreview.app.di

import android.app.Activity

/**
 * Created by jpp on 2/14/18.
 */
interface HasActivitySubcomponentBuilders {
    fun getActivityComponentBuilder(activityClass: Class<out Activity>): ActivityComponentBuilder<*, *>
}