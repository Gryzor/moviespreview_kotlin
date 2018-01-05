package com.jpp.moviespreview.app.ui.sections.detail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.transition.Slide
import android.widget.ImageView
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.util.extentions.app
import com.jpp.moviespreview.app.util.extentions.loadImageUrlWithCallback
import kotlinx.android.synthetic.main.movie_detail_activity.*
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * Shows the details of a given Movie.
 * Performs an activity transition between the Movies list in the previous screen and this one.
 *
 * Created by jpp on 11/11/17.
 */
class MovieDetailActivity : AppCompatActivity(), MovieDetailImagesView {

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


    private val component by lazy { app.movieDetailsComponent() }

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

        component.inject(this)
    }

    override fun onResume() {
        super.onResume()
        imagesPresenter.linkView(this)

        movie_details_body_view_pager.adapter = MovieDetailsViewPagerAdapter(supportFragmentManager, resources)
        movie_details_body_tab_layout.setupWithViewPager(movie_details_body_view_pager)
    }


    override fun showMovieNotSelected() {
        toast("error")
    }

    override fun showMovieImage(imageUrl: String) {
        iv_movie_details.loadImageUrlWithCallback(imageUrl,
                { supportStartPostponedEnterTransition() },
                {
                    iv_movie_details.setImageResource(R.drawable.ic_error_black)
                    supportStartPostponedEnterTransition()
                })
    }

    override fun showMovieTitle(movieTitle: String) {
        movie_details_collapsing_toolbar_layout.title = movieTitle
        movie_details_collapsing_toolbar_layout.setExpandedTitleColor(resources.getColor(android.R.color.transparent))
        supportActionBar!!.title = movieTitle
    }


    private fun initActivityTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val slideTransition = Slide()
            slideTransition.excludeTarget(android.R.id.statusBarBackground, true)
            window.enterTransition = slideTransition
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            overridePendingTransition(R.anim.activity_enter_transition, R.anim.activity_exit_transition)
        }
    }
}