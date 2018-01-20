package com.jpp.moviespreview.app.ui.sections.about

/**
 * Created by jpp on 1/17/18.
 */
class AboutPresenterImpl(private val aboutInteractor: AboutInteractor) : AboutPresenter {

    lateinit var viewInstance: AboutView

    override fun linkView(aboutView: AboutView) {
        viewInstance = aboutView
        aboutView.showAppVersion(aboutInteractor.getAppVersion())
        aboutView.showActions(aboutInteractor.getActions())
    }

    override fun onActionSelected(action: AboutAction) {
        when (action.type) {
            AboutAction.RATE_APP -> viewInstance.onRateApplicationSelected()
            AboutAction.SHARE_APP -> viewInstance.onShareApplicationSelected()
            AboutAction.LICENSES -> viewInstance.navigateToLicenses()
            AboutAction.BROWSE_CODE -> viewInstance.navigateToAppCode()
        }
    }

}