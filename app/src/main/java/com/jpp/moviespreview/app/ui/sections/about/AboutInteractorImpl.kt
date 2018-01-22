package com.jpp.moviespreview.app.ui.sections.about

import android.content.Context
import com.jpp.moviespreview.BuildConfig
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.ui.sections.about.AboutAction.Companion.BROWSE_CODE
import com.jpp.moviespreview.app.ui.sections.about.AboutAction.Companion.LICENSES
import com.jpp.moviespreview.app.ui.sections.about.AboutAction.Companion.RATE_APP
import com.jpp.moviespreview.app.ui.sections.about.AboutAction.Companion.SHARE_APP
import com.jpp.moviespreview.app.ui.sections.about.AboutAction.Companion.THE_MOVIE_DB_TERMS_OF_USE

/**
 * AboutInteractor implementation
 *
 * Created by jpp on 1/17/18.
 */
class AboutInteractorImpl(private val context: Context) : AboutInteractor {


    override fun getAppVersion(): String = BuildConfig.VERSION_NAME


    override fun getActions(): List<AboutAction> = listOf(
            AboutAction(context.getString(R.string.rate_app_action), R.drawable.ic_rate_app, RATE_APP),
            AboutAction(context.getString(R.string.share_app_action), R.drawable.ic_share_app, SHARE_APP),
            AboutAction(context.getString(R.string.brows_code_action), R.drawable.ic_github_logo, BROWSE_CODE),
            AboutAction(context.getString(R.string.open_source_action), R.drawable.ic_open_source_libraries, LICENSES),
            AboutAction(context.getString(R.string.the_movie_db_terms_of_use), R.drawable.ic_the_movie_db, THE_MOVIE_DB_TERMS_OF_USE)
    )

}