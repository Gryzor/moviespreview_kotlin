package com.jpp.moviespreview.app.ui.sections.search.di

import com.jpp.moviespreview.app.domain.MultiSearchPage
import com.jpp.moviespreview.app.domain.MultiSearchParam
import com.jpp.moviespreview.app.domain.UseCase
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.MoviesContext
import com.jpp.moviespreview.app.ui.interactors.PresenterInteractorDelegate
import com.jpp.moviespreview.app.ui.sections.search.MultiSearchPresenter
import com.jpp.moviespreview.app.ui.sections.search.MultiSearchPresenterImpl
import com.jpp.moviespreview.app.ui.sections.search.QuerySubmitManager
import com.jpp.moviespreview.app.ui.sections.search.QuerySubmitManagerImpl
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
    fun providesMultiSearchPresenter(moviesContext: MoviesContext,
                                     interactorDelegate: PresenterInteractorDelegate,
                                     mapper: DomainToUiDataMapper,
                                     querySubmitManager: QuerySubmitManager,
                                     useCase: UseCase<MultiSearchParam, MultiSearchPage>): MultiSearchPresenter
            = MultiSearchPresenterImpl(moviesContext, interactorDelegate, mapper, querySubmitManager, useCase)
}