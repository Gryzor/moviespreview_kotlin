package com.jpp.moviespreview.app

import android.app.Activity
import android.support.v4.app.Fragment
import com.jpp.moviespreview.app.di.activity.ActivityComponentBuilder
import com.jpp.moviespreview.app.di.fragment.FragmentComponentBuilder
import javax.inject.Provider

/**
 * [MoviesPreviewApp] implementation for Espresso tests.
 *
 * With this class we're able to inject mocked objects into the Dagger sub-components.
 *
 * Created by jpp on 2/14/18.
 */
class EspressoMoviesPreviewApp : MoviesPreviewApp() {


    /**
     * Replaces the [ActivityComponentBuilder] instance of the application class with the
     * provided [builder].
     * Convenience method to provide builders that will allow injecting mocked objects into
     * Espresso tests.
     */
    fun putActivityComponentBuilder(builder: ActivityComponentBuilder<*, *>, cls: Class<out Activity>) {
        val activityComponentBuilders = HashMap(this.activityComponentBuilders)
        activityComponentBuilders[cls] = Provider { builder }
        this.activityComponentBuilders = activityComponentBuilders
    }


    /**
     * Replaces the [FragmentComponentBuilder] instance of the application class with the provided
     * [builder].
     * This is a convenience method to inject builders that will allow the injection of mocked
     * objects into the Espresso tests.
     */
    fun putFragmentComponentBuilder(builder: FragmentComponentBuilder<*, *>, cls: Class<out Fragment>) {
        val fragmentComponentBuilders = HashMap(this.fragmentComponentBuilders)
        fragmentComponentBuilders[cls] = Provider { builder }
        this.fragmentComponentBuilders = fragmentComponentBuilders
    }

}
