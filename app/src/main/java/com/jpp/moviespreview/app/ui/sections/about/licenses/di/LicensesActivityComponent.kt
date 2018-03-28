package com.jpp.moviespreview.app.ui.sections.about.licenses.di

import com.jpp.moviespreview.app.di.activity.ActivityComponent
import com.jpp.moviespreview.app.di.activity.ActivityComponentBuilder
import com.jpp.moviespreview.app.di.activity.ActivityModule
import com.jpp.moviespreview.app.di.activity.ActivityScope
import com.jpp.moviespreview.app.domain.Command
import com.jpp.moviespreview.app.domain.Licenses
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.MoviesContextHandler
import com.jpp.moviespreview.app.ui.interactors.BackgroundExecutor
import com.jpp.moviespreview.app.ui.sections.about.licenses.*
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
        fun providesLicencesPresenter(moviesContextHandler: MoviesContextHandler,
                                      interactor: LicensesPresenterInteractor,
                                      backgroundExecutor: BackgroundExecutor,
                                      licencesFlowResolver: LicencesFlowResolver)
                : LicensesPresenter = LicensesPresenterImpl(moviesContextHandler, interactor, backgroundExecutor, licencesFlowResolver)

        @ActivityScope
        @Provides
        fun providesLicensesPresenterInteractor(mapper: DomainToUiDataMapper,
                                                retrieveLicencesCommand: Command<Any, Licenses>)
                : LicensesPresenterInteractor = LicensesPresenterInteractorImpl(mapper, retrieveLicencesCommand)

        @ActivityScope
        @Provides
        fun providesLicencesFlowResolver(): LicencesFlowResolver = activity
    }

}