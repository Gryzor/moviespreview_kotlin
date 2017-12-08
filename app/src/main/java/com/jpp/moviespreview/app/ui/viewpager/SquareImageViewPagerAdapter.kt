package com.jpp.moviespreview.app.ui.viewpager

import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.jpp.moviespreview.app.ui.views.SquareImageView

/**
 *
 * [PagerAdapter] implementation to show a given number of [SquareImageView].
 * The [size] will provide the number of [SquareImageView] to show. Each time a new [SquareImageView]
 * is loaded, the [imageViewProcessor] lambda will be called.
 *
 * Created by jpp on 12/5/17.
 */
class SquareImageViewPagerAdapter(private val size: Int,
                                  private val imageViewProcessor: (ImageView, Int) -> Unit) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = SquareImageView(container.context)
        imageViewProcessor(imageView, position)
        (container as ViewPager).addView(imageView)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any?) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View?, `object`: Any?) = view == `object` as View

    override fun getCount() = size
}