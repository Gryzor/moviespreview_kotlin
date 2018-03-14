package com.jpp.moviespreview.app.ui.sections.splash

import com.jpp.moviespreview.app.di.HasSubcomponentBuilders
import com.jpp.moviespreview.app.di.activity.InjectedActivity
import com.jpp.moviespreview.app.ui.sections.splash.di.SplashActivityComponent
import com.jpp.moviespreview.app.util.extentions.showNoNetworkConnectionAlert
import com.jpp.moviespreview.app.util.extentions.showUnexpectedError
import javax.inject.Inject

/**
 * Activity shown on startup. It implements SplashView in order to provide
 * an MVP implementation at this level.
 *
 * The Presenter involved in the splash will attempt to retrieve the initial configuration
 * to continue the application flow.
 *
 * Created by jpp on 10/4/17.
 */
class SplashActivity : InjectedActivity(), SplashView {


    override fun injectMembers(hasSubcomponentBuilders: HasSubcomponentBuilders) {
        (hasSubcomponentBuilders.getActivityComponentBuilder(SplashActivity::class.java) as SplashActivityComponent.Builder)
                .activityModule(SplashActivityComponent.SplashActivityModule(this)).build().injectMembers(this)
    }


    @Inject
    lateinit var splashPresenter: SplashPresenter

    override fun onResume() {
        super.onResume()
        splashPresenter.linkView(this)
    }

    override fun showUnexpectedError() {
        showUnexpectedError { finish() }
    }

    override fun showNotConnectedToNetwork() {
        showNoNetworkConnectionAlert()
    }

}