package com.jpp.moviespreview.app.di

/**
 * Created by jpp on 2/14/18.
 */
interface ActivityComponentBuilder<M : ActivityModule<*>, C : ActivityComponent<*>> {
    fun activityModule(activityModule: M): ActivityComponentBuilder<M, C>
    fun build(): C
}