package com.jpp.moviespreview.app.ui.sections.about

/**
 * Created by jpp on 1/17/18.
 */
interface AboutView {
    fun showAppVersion(appVersion: String)
}

interface AboutPresenter {
    fun linkView(aboutView: AboutView)
}

interface AboutInteractor {
    fun getAppVersion(): String
}