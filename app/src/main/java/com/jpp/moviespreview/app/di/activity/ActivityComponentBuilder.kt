package com.jpp.moviespreview.app.di.activity

/**
 * Based on http://frogermcs.github.io/activities-multibinding-in-dagger-2/
 *
 * Defines the signature of the builder that will build the modules and components
 * for the activities.
 *
 * Created by jpp on 2/14/18.
 */
interface ActivityComponentBuilder<M : ActivityModule<*>, C : ActivityComponent<*>> {
    fun activityModule(activityModule: M): ActivityComponentBuilder<M, C>
    fun build(): C
}