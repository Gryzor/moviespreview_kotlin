package com.jpp.moviespreview.app.ui.splash.di

import com.jpp.moviespreview.app.ui.splash.SplashPresenter
import com.jpp.moviespreview.app.ui.splash.SplashPresenterImpl
import dagger.Module
import dagger.Provides

/**
 * Splash scope module.
 *
 * Created by jpp on 10/4/17.
 */
@Module
class SplashModule() {

    @Provides
    @SplashScope
    fun providesSplashPresenter(): SplashPresenter = SplashPresenterImpl()


}