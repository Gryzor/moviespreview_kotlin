package com.jpp.moviespreview.app.ui.sections.about

import com.jpp.moviespreview.BuildConfig

/**
 * AboutInteractor implementation
 *
 * Created by jpp on 1/17/18.
 */
class AboutInteractorImpl() : AboutInteractor {


    override fun getAppVersion(): String = BuildConfig.VERSION_NAME

}