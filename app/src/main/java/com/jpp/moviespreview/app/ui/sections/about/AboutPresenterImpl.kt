package com.jpp.moviespreview.app.ui.sections.about

/**
 * Created by jpp on 1/17/18.
 */
class AboutPresenterImpl(private val aboutInteractor: AboutInteractor) : AboutPresenter {

    override fun linkView(aboutView: AboutView) {
        aboutView.showAppVersion(aboutInteractor.getAppVersion())
        aboutView.showActions(aboutInteractor.getActions())
    }

    override fun onActionSelected(action: AboutAction) {
        //TODO
    }

}