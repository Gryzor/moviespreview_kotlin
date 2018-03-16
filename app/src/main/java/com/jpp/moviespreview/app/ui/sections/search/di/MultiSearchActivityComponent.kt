package com.jpp.moviespreview.app.ui.sections.search.di

import com.jpp.moviespreview.app.di.activity.ActivityComponent
import com.jpp.moviespreview.app.di.activity.ActivityComponentBuilder
import com.jpp.moviespreview.app.di.activity.ActivityModule
import com.jpp.moviespreview.app.di.activity.ActivityScope
import com.jpp.moviespreview.app.domain.MultiSearchPage
import com.jpp.moviespreview.app.domain.MultiSearchParam
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.interactors.ImageConfigurationManager
import com.jpp.moviespreview.app.ui.interactors.ImageConfigurationManagerImpl
import com.jpp.moviespreview.app.ui.interactors.PaginationController
import com.jpp.moviespreview.app.ui.interactors.PresenterInteractorDelegate
import com.jpp.moviespreview.app.ui.sections.search.*
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

/**
 * [ActivityComponent] for the multi search module.
 *
 * Created by jpp on 3/16/18.
 */
@ActivityScope
@Subcomponent(modules = [(MultiSearchActivityComponent.MultiSearchActivityModule::class)])
interface MultiSearchActivityComponent : ActivityComponent<MultiSearchActivity> {

    @Subcomponent.Builder
    interface Builder : ActivityComponentBuilder<MultiSearchActivityModule, MultiSearchActivityComponent>


    @Module
    class MultiSearchActivityModule internal constructor(activity: MultiSearchActivity) : ActivityModule<MultiSearchActivity>(activity) {
        @Provides
        @ActivityScope
        fun providesQuerySubmitManager(): QuerySubmitManager = QuerySubmitManagerImpl()


        @Provides
        @ActivityScope
        fun providesMultiSearchPresenter(multiSearchContext: MultiSearchContext,
                                         interactorDelegate: MultiSearchPresenterController,
                                         mapper: DomainToUiDataMapper,
                                         querySubmitManager: QuerySubmitManager,
                                         useCase: UseCase<MultiSearchParam, MultiSearchPage>): MultiSearchPresenter = MultiSearchPresenterImpl(multiSearchContext, interactorDelegate, mapper, querySubmitManager, useCase)

        @Provides
        @ActivityScope
        fun providesMultiSearchPresenterController(presenterInteractorDelegate: PresenterInteractorDelegate,
                                                   imageConfigurationManager: ImageConfigurationManager,
                                                   paginationController: PaginationController): MultiSearchPresenterController = MultiSearchPresenterControllerImpl(presenterInteractorDelegate, imageConfigurationManager, paginationController)


        @Provides
        @ActivityScope
        fun providesImageConfigurationManager(): ImageConfigurationManager = ImageConfigurationManagerImpl()
    }

}