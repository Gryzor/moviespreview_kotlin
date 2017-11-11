package com.jpp.moviespreview.app.ui.detail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
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
 * Created by jpp on 11/11/17.
 */
class MovieDetailActivity : AppCompatActivity(), MovieDetailView {


    companion object {

        private val EXTRA_TRANSITION_NAME = "com.jpp.moviespreview.app.ui.detail.EXTRA_TRANSITION_NAME"

        fun navigateWithTransition(activity: AppCompatActivity, transitionImage: ImageView) {
            val intent = Intent(activity, MovieDetailActivity::class.java)
            intent.putExtra(EXTRA_TRANSITION_NAME, ViewCompat.getTransitionName(transitionImage))
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, ViewCompat.getTransitionName(transitionImage))
            ActivityCompat.startActivity(activity, intent, options.toBundle())
        }

    }


    private val component by lazy { app.movieDetailsComponent() }

    @Inject
    lateinit var presenter: MovieDetailPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityTransition()
        setContentView(R.layout.movie_detail_activity)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val transitionName = intent.getStringExtra(EXTRA_TRANSITION_NAME)
            ViewCompat.setTransitionName(movie_details_app_bar_layout, transitionName)
        }

        supportPostponeEnterTransition()

        setSupportActionBar(movie_details_toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        movie_details_collapsin_toolbar_layout.setExpandedTitleColor(resources.getColor(android.R.color.transparent))

        component.inject(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.linkView(this)
    }

    override fun showMovieImages(vararg urls: String) {
        iv_movie_details.loadImageUrlWithCallback(urls[0], { supportStartPostponedEnterTransition() })
    }

    override fun showMovieNotSelected() {
        toast("error")
    }


    private fun initActivityTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val slideTransition = Slide()
            slideTransition.excludeTarget(android.R.id.statusBarBackground, true)
            window.enterTransition = slideTransition
//            window.returnTransition = slideTransition
        }
    }

}