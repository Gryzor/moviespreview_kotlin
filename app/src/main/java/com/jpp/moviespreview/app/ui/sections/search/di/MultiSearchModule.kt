package com.jpp.moviespreview.app.ui.sections.search.di

import com.jpp.moviespreview.app.domain.MultiSearchPage
import com.jpp.moviespreview.app.domain.MultiSearchParam
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.interactors.ImageConfigurationInteractor
import com.jpp.moviespreview.app.ui.interactors.ImageConfigurationInteractorImpl
import com.jpp.moviespreview.app.ui.interactors.PaginationInteractor
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
                                     interactorDelegate: MultiSearchPresenterInteractor,
                                     mapper: DomainToUiDataMapper,
                                     querySubmitManager: QuerySubmitManager,
                                     useCase: UseCase<MultiSearchParam, MultiSearchPage>): MultiSearchPresenter
            = MultiSearchPresenterImpl(multiSearchContext, interactorDelegate, mapper, querySubmitManager, useCase)

    @Provides
    @MultiSearchScope
    fun providesPlayingMoviesPresenterInteractor(presenterInteractorDelegate: PresenterInteractorDelegate,
                                                 imageConfigurationInteractor: ImageConfigurationInteractor,
                                                 paginationInteractor: PaginationInteractor): MultiSearchPresenterInteractor
            = MultiSearchPresenterInteractorImpl(presenterInteractorDelegate, imageConfigurationInteractor, paginationInteractor)


    @Provides
    @MultiSearchScope
    fun providesImageConfigurationPresenterDelegate(): ImageConfigurationInteractor
            = ImageConfigurationInteractorImpl()
}