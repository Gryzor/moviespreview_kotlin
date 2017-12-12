package com.jpp.moviespreview.app.ui.detail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.transition.Slide
import android.widget.ImageView
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.ui.viewpager.SquareImageViewPagerAdapter
import com.jpp.moviespreview.app.util.extentions.app
import com.jpp.moviespreview.app.util.extentions.loadImageUrlWithCallback
import com.jpp.moviespreview.app.util.extentions.pageChangeUpdate
import com.jpp.moviespreview.app.util.extentions.setCurrentItemDelayed
import kotlinx.android.synthetic.main.movie_detail_activity.*
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * Shows the details of a given Movie.
 * Performs an activity transition between the Movies list in the previous screen and this one.
 * TODO 1 - problem: select a movie from the list, change the selected image in detals, go back to movie list
 * TODO 1bis - refactor xml to extract common values
 * TODO 2 - transition vp indicator
 * TODO 3 - Transition Movie title
 * TODO 4 - Tint with pallete
 * TODO 5 - transition view number
 * TODO 6 - transition likes number
 * TODO 7 - show details
 *
 * Created by jpp on 11/11/17.
 */
class MovieDetailActivity : AppCompatActivity(), MovieDetailView {

    companion object {

        private val EXTRA_TRANSITION_NAME = "com.jpp.moviespreview.app.ui.detail.EXTRA_TRANSITION_NAME"

        fun navigateWithTransition(activity: AppCompatActivity, transitionViewPager: ViewPager) {
            val intent = Intent(activity, MovieDetailActivity::class.java)
            intent.putExtra(EXTRA_TRANSITION_NAME, ViewCompat.getTransitionName(transitionViewPager))
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionViewPager, ViewCompat.getTransitionName(transitionViewPager))
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

    override fun showMovieImages(imagesUrl: List<String>, selectedPosition: Int) {
        vp_movie_details.adapter = SquareImageViewPagerAdapter(imagesUrl.size, { imageView: ImageView, position: Int ->
            imageView.loadImageUrlWithCallback(imagesUrl[position],
                    {
                        vp_movie_details.setCurrentItemDelayed(selectedPosition)
                        supportStartPostponedEnterTransition()
                    })
        })
        vp_movie_details.pageChangeUpdate { position: Int -> presenter.onMovieImageSelected(position) }
        vp_movie_details_tab_indicator.setupWithViewPager(vp_movie_details, true)
    }

    override fun showMovieNotSelected() {
        toast("error")
    }


    private fun initActivityTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val slideTransition = Slide()
            slideTransition.excludeTarget(android.R.id.statusBarBackground, true)
            window.enterTransition = slideTransition
        }
    }

}