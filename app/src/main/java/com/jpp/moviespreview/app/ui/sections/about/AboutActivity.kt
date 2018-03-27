package com.jpp.moviespreview.app.ui.sections.about

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v7.widget.LinearLayoutManager
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.di.HasSubcomponentBuilders
import com.jpp.moviespreview.app.di.activity.InjectedActivity
import com.jpp.moviespreview.app.ui.sections.about.di.AboutActivityComponent
import com.jpp.moviespreview.app.ui.sections.about.licenses.LicensesActivity
import com.jpp.moviespreview.app.util.extentions.whenNotNull
import kotlinx.android.synthetic.main.about_activity.*
import javax.inject.Inject

/**
 * Created by jpp on 1/17/18.
 */
class AboutActivity : InjectedActivity(), AboutView {


    override fun injectMembers(hasSubcomponentBuilders: HasSubcomponentBuilders) {
        (hasSubcomponentBuilders.getActivityComponentBuilder(AboutActivity::class.java) as AboutActivityComponent.Builder)
                .activityModule(AboutActivityComponent.AboutActivityModule(this)).build().injectMembers(this)
    }


    companion object {
        val APP_WEB_URL = "https://play.google.com/store/apps/details"
        val APP_MARKET_URI = "market://details"
        val CODE_REPOSITORY_URL = "https://github.com/perettijuan/moviespreview_kotlin"
        val API_TERM_OF_USE_URL = "https://www.themoviedb.org/documentation/api/terms-of-use?"
    }

    @Inject
    lateinit var presenter: AboutPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_activity)

        whenNotNull(supportActionBar) {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = getString(R.string.about_menu_item)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.linkView(this)
    }

    override fun showAppVersion(appVersion: String) {
        about_screen_version_text_view.text = getString(R.string.about_version, appVersion)
    }

    override fun showActions(actions: List<AboutAction>) {
        val adapter = AboutActionsAdapter(actions, { presenter.onActionSelected(it) })
        rv_about_screen.layoutManager = LinearLayoutManager(this)
        rv_about_screen.adapter = adapter
    }

    override fun onRateApplicationSelected() {
        try {
            startActivity(rateIntentForUrl(APP_MARKET_URI))
        } catch (e: ActivityNotFoundException) {
            startActivity(rateIntentForUrl(APP_WEB_URL))
        }
    }

    override fun onShareApplicationSelected() {
        val sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                getString(R.string.share_app_text, Uri.parse(String.format("%s?id=%s", APP_WEB_URL, packageName))))
        sendIntent.type = "text/plain"
        startActivity(sendIntent)
    }

    override fun navigateToAppCode() {
        navigateInnerBrowser(CODE_REPOSITORY_URL)
    }

    override fun navigateToLicenses() {
        startActivity(Intent(this, LicensesActivity::class.java))
    }

    override fun navigateToApiTermsOfUse() {
        navigateInnerBrowser(API_TERM_OF_USE_URL)
    }


    private fun navigateInnerBrowser(uriString: String) {
        val navigateIntentBuilder = CustomTabsIntent.Builder()
        navigateIntentBuilder.setToolbarColor(resources.getColor(R.color.colorPrimary))
        navigateIntentBuilder.setStartAnimations(this, R.anim.activity_enter_transition, R.anim.activity_exit_transition)
        navigateIntentBuilder.setExitAnimations(this, R.anim.activity_enter_transition, R.anim.activity_exit_transition)
        val navigateIntent = navigateIntentBuilder.build()
        navigateIntent.launchUrl(this, Uri.parse(uriString))
    }

    private fun rateIntentForUrl(url: String): Intent {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, packageName)))
        var flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            flags or Intent.FLAG_ACTIVITY_NEW_DOCUMENT
        } else {
            flags or Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET
        }
        intent.addFlags(flags)
        return intent
    }
}