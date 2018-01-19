package com.jpp.moviespreview.app.ui.sections.about.di

import android.content.Context
import com.jpp.moviespreview.app.ui.sections.about.AboutInteractor
import com.jpp.moviespreview.app.ui.sections.about.AboutInteractorImpl
import com.jpp.moviespreview.app.ui.sections.about.AboutPresenter
import com.jpp.moviespreview.app.ui.sections.about.AboutPresenterImpl
import dagger.Module
import dagger.Provides

/**
 * Dagger module implementation for the about section
 *
 * Created by jpp on 1/17/18.
 */
@Module
class AboutModule {

    @AboutScope
    @Provides
    fun providesAboutInteractor(context: Context): AboutInteractor = AboutInteractorImpl(context)


    @AboutScope
    @Provides
    fun providesAboutPresenter(aboutInteractor: AboutInteractor): AboutPresenter = AboutPresenterImpl(aboutInteractor)

}