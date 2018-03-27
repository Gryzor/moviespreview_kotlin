package com.jpp.moviespreview.app.ui.sections.about.licenses.di

import com.jpp.moviespreview.app.di.activity.ActivityComponent
import com.jpp.moviespreview.app.di.activity.ActivityComponentBuilder
import com.jpp.moviespreview.app.di.activity.ActivityModule
import com.jpp.moviespreview.app.di.activity.ActivityScope
import com.jpp.moviespreview.app.domain.Licenses
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.ui.ApplicationMoviesContext
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.interactors.BackgroundInteractor
import com.jpp.moviespreview.app.ui.sections.about.licenses.LicensesActivity
import com.jpp.moviespreview.app.ui.sections.about.licenses.LicensesPresenter
import com.jpp.moviespreview.app.ui.sections.about.licenses.LicensesPresenterImpl
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

/**
 * [ActivityComponent] for the licenses section.
 *
 *
 * Created by jpp on 3/27/18.
 */
@ActivityScope
@Subcomponent(modules = [(LicensesActivityComponent.LicencesActivityModule::class)])
interface LicensesActivityComponent : ActivityComponent<LicensesActivity> {


    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<LicencesActivityModule, LicensesActivityComponent>

    @Module
    class LicencesActivityModule internal constructor(activity: LicensesActivity) : ActivityModule<LicensesActivity>(activity) {
        @ActivityScope
        @Provides
        fun providesLicencesPresenter(moviesContext: ApplicationMoviesContext,
                                      useCase: UseCase<Any, Licenses>,
                                      domainToUiDataMapper: DomainToUiDataMapper,
                                      backgroundInteractor: BackgroundInteractor): LicensesPresenter = LicensesPresenterImpl(moviesContext, useCase, domainToUiDataMapper, backgroundInteractor)
    }

}