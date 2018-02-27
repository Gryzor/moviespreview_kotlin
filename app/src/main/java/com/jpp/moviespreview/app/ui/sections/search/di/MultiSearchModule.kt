package com.jpp.moviespreview.app.ui.sections.search.di

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

/**
 * Provides dependencies for the search section.
 *
 * Created by jpp on 1/6/18.
 */
@Module
class MultiSearchModule {


    @Provides
    @MultiSearchScope
    fun providesQuerySubmitManager(): QuerySubmitManager = QuerySubmitManagerImpl()


    @Provides
    @MultiSearchScope
    fun providesMultiSearchPresenter(multiSearchContext: MultiSearchContext,
                                     interactorDelegate: MultiSearchPresenterController,
                                     mapper: DomainToUiDataMapper,
                                     querySubmitManager: QuerySubmitManager,
                                     useCase: UseCase<MultiSearchParam, MultiSearchPage>): MultiSearchPresenter
            = MultiSearchPresenterImpl(multiSearchContext, interactorDelegate, mapper, querySubmitManager, useCase)

    @Provides
    @MultiSearchScope
    fun providesMultiSearchPresenterController(presenterInteractorDelegate: PresenterInteractorDelegate,
                                                 imageConfigurationManager: ImageConfigurationManager,
                                                 paginationController: PaginationController): MultiSearchPresenterController
            = MultiSearchPresenterControllerImpl(presenterInteractorDelegate, imageConfigurationManager, paginationController)


    @Provides
    @MultiSearchScope
    fun providesImageConfigurationManager(): ImageConfigurationManager
            = ImageConfigurationManagerImpl()
}