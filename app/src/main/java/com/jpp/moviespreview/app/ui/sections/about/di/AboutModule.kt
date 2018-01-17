package com.jpp.moviespreview.app.ui.sections.about.di

import com.jpp.moviespreview.app.ui.sections.about.AboutInteractor
import com.jpp.moviespreview.app.ui.sections.about.AboutInteractorImpl
import com.jpp.moviespreview.app.ui.sections.about.AboutPresenter
import com.jpp.moviespreview.app.ui.sections.about.AboutPresenterImpl
import dagger.Module
import dagger.Provides

/**
 * Created by jpp on 1/17/18.
 */
@Module
class AboutModule {

    @AboutScope
    @Provides
    fun providesAboutInteractor(): AboutInteractor = AboutInteractorImpl()


    @AboutScope
    @Provides
    fun providesAboutPresenter(aboutInteractor: AboutInteractor): AboutPresenter = AboutPresenterImpl(aboutInteractor)

}