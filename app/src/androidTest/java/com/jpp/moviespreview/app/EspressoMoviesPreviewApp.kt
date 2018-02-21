package com.jpp.moviespreview.app

import android.app.Activity
import com.jpp.moviespreview.app.di.activity.ActivityComponentBuilder
import java.util.*
import javax.inject.Provider

/**
 * [MoviesPreviewApp] implementation for Espresso tests.
 *
 * With this class we're able to inject mocked objects into the Dagger sub-components.
 *
 * Created by jpp on 2/14/18.
 */
class EspressoMoviesPreviewApp : MoviesPreviewApp() {

    fun putActivityComponentBuilder(builder: ActivityComponentBuilder<*, *>, cls: Class<out Activity>) {
        val activityComponentBuilders = HashMap(this.activityComponentBuilders)
        activityComponentBuilders.put(cls, Provider { builder })
        this.activityComponentBuilders = activityComponentBuilders
    }

}
