package com.jpp.moviespreview.app.ui.sections.about.di

import com.jpp.moviespreview.app.ui.sections.about.AboutActivity
import dagger.Subcomponent

/**
 *
 * About sub-component
 *
 * Created by jpp on 1/17/18.
 */
@AboutScope
@Subcomponent(modules = arrayOf(AboutModule::class))
interface AboutComponent {

    fun inject(aboutActivity: AboutActivity)
}