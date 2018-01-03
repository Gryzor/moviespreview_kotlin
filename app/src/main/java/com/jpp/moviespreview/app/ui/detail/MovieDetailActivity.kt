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
import com.jpp.moviespreview.app.ui.viewpager.ImageViewPagerAdapter
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
 *
 * Created by jpp on 11/11/17.
 */
class MovieDetailActivity : AppCompatActivity(), MovieDetailImagesView {

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

    override fun showMovieImages(imagesUrl: List<String>, selectedPosition: Int) {
        movie_details_images_view_pager.adapter = ImageViewPagerAdapter(imagesUrl.size, { imageView: ImageView, position: Int ->
            imageView.loadImageUrlWithCallback(imagesUrl[position],
                    {
                        movie_details_images_view_pager.setCurrentItemDelayed(selectedPosition)
                        supportStartPostponedEnterTransition()
                    },
                    {
                        // on fail, just continue with the flow -> an error image will be set
                        supportStartPostponedEnterTransition()
                    })
        })
        movie_details_images_view_pager.pageChangeUpdate { position: Int -> imagesPresenter.onMovieImageSelected(position) }
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
}