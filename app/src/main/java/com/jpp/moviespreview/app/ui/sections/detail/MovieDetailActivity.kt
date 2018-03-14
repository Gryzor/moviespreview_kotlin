package com.jpp.moviespreview.app.ui.sections.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.Palette
import android.transition.Slide
import android.view.MenuItem
import android.widget.ImageView
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.di.HasSubcomponentBuilders
import com.jpp.moviespreview.app.di.activity.InjectedActivity
import com.jpp.moviespreview.app.ui.sections.detail.di.MovieDetailActivityComponent
import com.jpp.moviespreview.app.util.extentions.app
import com.jpp.moviespreview.app.util.extentions.loadImageUrlWithCallback
import kotlinx.android.synthetic.main.movie_detail_activity.*
import javax.inject.Inject

/**
 * Shows the details of a given Movie.
 * Performs an activity transition between the Movies list in the previous screen and this one.
 *
 * Created by jpp on 11/11/17.
 */
class MovieDetailActivity : InjectedActivity(), MovieDetailImagesView {


    override fun injectMembers(hasSubcomponentBuilders: HasSubcomponentBuilders) {
        (hasSubcomponentBuilders.getActivityComponentBuilder(MovieDetailActivity::class.java) as MovieDetailActivityComponent.Builder)
                .activityModule(MovieDetailActivityComponent.MovieDetailActivityModule(this)).build().injectMembers(this)
    }


    companion object {

        private val EXTRA_TRANSITION_NAME = "com.jpp.moviespreview.app.ui.detail.EXTRA_TRANSITION_NAME"

        fun navigateWithTransition(activity: AppCompatActivity, transitionView: ImageView) {
            val intent = Intent(activity, MovieDetailActivity::class.java)
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                activity.startActivity(intent)
                activity.overridePendingTransition(R.anim.activity_enter_transition, 0)
            } else {
                ViewCompat.setTransitionName(transitionView, "transitionView")
                intent.putExtra(EXTRA_TRANSITION_NAME, ViewCompat.getTransitionName(transitionView))
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionView, ViewCompat.getTransitionName(transitionView))
                activity.startActivity(intent, options.toBundle())
            }
        }
    }


    @Inject
    lateinit var imagesPresenter: MovieDetailImagesPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityTransition()
        setContentView(R.layout.movie_detail_activity)


        val transitionName = intent.getStringExtra(EXTRA_TRANSITION_NAME)
        ViewCompat.setTransitionName(movie_details_app_bar_layout, transitionName)
        supportPostponeEnterTransition()

        setSupportActionBar(movie_details_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        imagesPresenter.linkView(this)

        movie_details_body_view_pager.adapter = MovieDetailsViewPagerAdapter(supportFragmentManager, resources)
        movie_details_body_tab_layout.setupWithViewPager(movie_details_body_view_pager)
    }


    override fun showMovieNotSelected() {
        finish()
    }

    override fun showMovieImage(imageUrl: String) {
        iv_movie_details.loadImageUrlWithCallback(imageUrl,
                {
                    Palette.from(it).generate {
                        applyPalette(it)
                    }
                },
                {
                    iv_movie_details.setImageResource(R.drawable.ic_error_black)
                    supportStartPostponedEnterTransition()
                })
    }

    private fun applyPalette(palette: Palette) {
        val primaryDark = resources.getColor(R.color.colorPrimaryDark)
        val primary = resources.getColor(R.color.colorPrimary)
        movie_details_collapsing_toolbar_layout.setContentScrimColor(palette.getMutedColor(primary))
        movie_details_collapsing_toolbar_layout.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark))
        supportStartPostponedEnterTransition()
    }

    override fun showMovieTitle(movieTitle: String) {
        movie_details_collapsing_toolbar_layout.title = movieTitle
        movie_details_collapsing_toolbar_layout.setExpandedTitleColor(resources.getColor(android.R.color.transparent))
        supportActionBar!!.title = movieTitle
    }


    @SuppressLint("NewApi")
    private fun initActivityTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val slideTransition = Slide()
            slideTransition.excludeTarget(android.R.id.statusBarBackground, true)
            window.enterTransition = slideTransition
        }
    }

    /**
     * Override in order to provide activity transition when back button
     * is pressed.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        when (itemId) {
            android.R.id.home -> {
                super.onOptionsItemSelected(item)
                onBackPressed()
            }
        }
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            overridePendingTransition(R.anim.activity_enter_transition, R.anim.activity_exit_transition)
        }
    }
}