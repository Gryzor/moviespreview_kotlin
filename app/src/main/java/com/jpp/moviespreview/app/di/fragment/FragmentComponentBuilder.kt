package com.jpp.moviespreview.app.di.fragment

/**
 * Based on http://frogermcs.github.io/activities-multibinding-in-dagger-2/
 *
 * Defines the signature of the builder that will build the modules and components
 * for the Fragments in the app.
 *
 * Created by jpp on 2/21/18.
 */
interface FragmentComponentBuilder<M : FragmentModule<*>, C : FragmentComponent<*>> {
    fun fragmentModule(fragmentModule: M): FragmentComponentBuilder<M, C>
    fun build(): C
}