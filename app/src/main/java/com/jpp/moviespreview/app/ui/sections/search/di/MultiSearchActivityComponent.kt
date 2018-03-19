package com.jpp.moviespreview.app.ui.sections.search.di

import com.jpp.moviespreview.app.di.activity.ActivityComponent
import com.jpp.moviespreview.app.di.activity.ActivityComponentBuilder
import com.jpp.moviespreview.app.di.activity.ActivityModule
import com.jpp.moviespreview.app.di.activity.ActivityScope
import com.jpp.moviespreview.app.domain.Command
import com.jpp.moviespreview.app.domain.MultiSearchPage
import com.jpp.moviespreview.app.domain.MultiSearchParam
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.interactors.*
import com.jpp.moviespreview.app.ui.sections.search.*
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

/**
 * [ActivityComponent] for the multi searchFirstPage module.
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
                                         querySubmitManager: QuerySubmitManager,
                                         backgroundExecutor: BackgroundExecutor,
                                         imageConfigManager: ImageConfigurationManager,
                                         paginationController: PaginationController,
                                         interactor: MultiSearchInteractor): MultiSearchPresenter =
                MultiSearchPresenterImpl(multiSearchContext, querySubmitManager, backgroundExecutor, imageConfigManager, paginationController, interactor)

        @Provides
        @ActivityScope
        fun providesImageConfigurationManager(): ImageConfigurationManager = ImageConfigurationManagerImpl()

        @Provides
        @ActivityScope
        fun providesInteractor(mapper: DomainToUiDataMapper,
                               connectivityInteractor: ConnectivityInteractor,
                               command: Command<@JvmSuppressWildcards MultiSearchParam, MultiSearchPage>): MultiSearchInteractor =
                MultiSearchInteractorImpl(mapper, connectivityInteractor, command)
    }

}