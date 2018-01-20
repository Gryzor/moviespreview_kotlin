package com.jpp.moviespreview.app.ui.sections.about

import android.support.annotation.DrawableRes
import android.support.annotation.IntDef

/**
 * Created by jpp on 1/17/18.
 */
interface AboutView {
    fun showAppVersion(appVersion: String)
    fun showActions(actions: List<AboutAction>)
    fun onRateApplicationSelected()
    fun onShareApplicationSelected()
    fun navigateToAppCode()
    fun navigateToLicenses()
}

interface AboutPresenter {
    fun linkView(aboutView: AboutView)
    fun onActionSelected(action: AboutAction)
}

interface AboutInteractor {
    fun getAppVersion(): String
    fun getActions(): List<AboutAction>
}


/**
 * This is a very special data class, that does
 * not belong to the model of the application's domain,
 * since it's only used by the about section to map
 * the possible actions of the UI.
 */
data class AboutAction(val title: String,
                       @DrawableRes val icon: Int,
                       @ActionType val type: Long) {
    companion object {
        @IntDef(RATE_APP, SHARE_APP, BROWSE_CODE, LICENSES)
        @Retention(AnnotationRetention.SOURCE)
        annotation class ActionType

        const val RATE_APP = 0L
        const val SHARE_APP = 1L
        const val BROWSE_CODE = 2L
        const val LICENSES = 3L
    }
}