package com.jpp.moviespreview.app.ui.viewpager

import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.jpp.moviespreview.R

/**
 *
 * [PagerAdapter] implementation to show a given number of [ImageView].
 * The [size] will provide the number of [ImageView] to show. Each time a new [ImageView]
 * is loaded, the [imageViewProcessor] lambda will be called.
 *
 * Created by jpp on 12/5/17.
 */
class ImageViewPagerAdapter(private val size: Int,
                            private val imageViewProcessor: (ImageView, Int) -> Unit) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(container.context)
        imageViewProcessor(imageView, position)
        imageView.setImageResource(R.drawable.ic_app_icon_black)
        (container as ViewPager).addView(imageView)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any?) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View?, `object`: Any?) = view == `object` as View

    override fun getCount() = size
}