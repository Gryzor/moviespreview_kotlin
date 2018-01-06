package com.jpp.moviespreview.app.ui.sections.main.search.di

import com.jpp.moviespreview.app.ui.sections.main.search.MultiSearchActivity
import dagger.Subcomponent

/**
 * Created by jpp on 1/6/18.
 */
@MultiSearchScope
@Subcomponent(modules = arrayOf(MultiSearchModule::class))
interface MultiSearchComponent {
    fun inject(multiSearchActivity: MultiSearchActivity)
}