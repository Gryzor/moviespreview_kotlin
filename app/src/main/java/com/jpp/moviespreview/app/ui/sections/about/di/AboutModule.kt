package com.jpp.moviespreview.app.ui.sections.about.di

import android.content.Context
import com.jpp.moviespreview.app.domain.Licenses
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.interactors.BackgroundInteractor
import com.jpp.moviespreview.app.ui.sections.about.AboutInteractor
import com.jpp.moviespreview.app.ui.sections.about.AboutInteractorImpl
import com.jpp.moviespreview.app.ui.sections.about.AboutPresenter
import com.jpp.moviespreview.app.ui.sections.about.AboutPresenterImpl
import com.jpp.moviespreview.app.ui.sections.about.licenses.LicensesPresenter
import com.jpp.moviespreview.app.ui.sections.about.licenses.LicensesPresenterImpl
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


    @AboutScope
    @Provides
    fun providesLicencesPresenter(moviesContext: MoviesContext,
                                  useCase: UseCase<Any, Licenses>,
                                  domainToUiDataMapper: DomainToUiDataMapper,
                                  backgroundInteractor: BackgroundInteractor): LicensesPresenter = LicensesPresenterImpl(moviesContext, useCase, domainToUiDataMapper, backgroundInteractor)

}