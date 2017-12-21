package com.jpp.moviespreview.app.util.extentions

import android.content.Context
import android.graphics.Bitmap
import android.support.v4.graphics.drawable.RoundedBitmapDrawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.jpp.moviespreview.R
import java.lang.Exception

/**
 * Inflates a given layout resources and returns the inflated view.
 */
fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

/**
 * Extension function for the View class : returns the Context of the View
 */
val View.ctx: Context
    get() = context

/**
 * Loads an image retrieved from the provided [imageUrl]
 * into the ImageView.
 */
fun ImageView.loadImageUrl(imageUrl: String) {
    Glide.with(ctx)
            .load(imageUrl)
            .placeholder(R.drawable.ic_app_icon_black)
            .error(R.drawable.ic_error_black)
            .centerCrop()
            .into(this)
}

/**
 * Loads an image retrieved from the provided [imageUrl]
 * into the ImageView and executes the [callback] once that the
 * image has been loaded.
 */
fun ImageView.loadImageUrlWithCallback(imageUrl: String,
                                       callback: (Bitmap) -> Unit,
                                       errorListener: ((Exception?) -> Unit)? = null) {
    Glide.with(ctx)
            .load(imageUrl)
            .asBitmap()
            .listener(ImageLoadListener(errorListener))
            .into(CallbackTarget(this, callback))
}

fun ImageView.loadCircularImageView(imageUrl: String) {
    Glide.with(ctx)
            .load(imageUrl)
            .asBitmap()
            .centerCrop()
            .into(CircularImageViewTransformation(this))
}

private class CircularImageViewTransformation(private val imageView: ImageView) : BitmapImageViewTarget(imageView) {
    override fun setResource(resource: Bitmap) {
        val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(imageView.ctx.resources, resource)
        circularBitmapDrawable.isCircular = true
        imageView.setImageDrawable(circularBitmapDrawable)
    }
}

/**
 * Inner callback class
 */
private class CallbackTarget(private val target: ImageView,
                             private val callback: (Bitmap) -> Unit) : SimpleTarget<Bitmap>() {

    override fun onResourceReady(resource: Bitmap, glideAnimation: GlideAnimation<in Bitmap>) {
        target.setImageBitmap(resource)
        target.scaleType = ImageView.ScaleType.CENTER_CROP
        callback(resource)
    }

}

/**
 * [RequestListener] to get a callback when a image loading fails.
 */
private class ImageLoadListener(private val errorListener: ((Exception?) -> Unit)?) : RequestListener<String, Bitmap> {
    override fun onException(e: Exception?, model: String?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
        errorListener?.invoke(e)
        return true
    }

    override fun onResourceReady(resource: Bitmap?, model: String?, target: Target<Bitmap>?, isFromMemoryCache: Boolean, isFirstResource: Boolean) = false
}

/**
 * Extension function for the RecyclerView class that allows detecting endless scrolling.
 * It will add a scrolling listener to the RecyclerView and execute the thresholdReached function
 * when the scrolling reaches the threshold
 */
fun RecyclerView.endlessScrolling(thresholdReached: () -> Unit, threshold: Int = 5) {
    addOnScrollListener(EndlessScrollListener(threshold, thresholdReached))
}


/**
 * Inner definition of a RecyclerView.OnScrollListener that evaluates the scrolling and executes
 * a function if a threshold is reached.
 */
private class EndlessScrollListener(val threshold: Int,
                                    val thresholdReached: () -> Unit) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dy > 0) {
            // only works with linear layout managers
            val layoutManager = recyclerView?.layoutManager as? LinearLayoutManager ?: return

            val visibleItemCount = layoutManager.childCount
            val pastVisibleItems = layoutManager.findFirstCompletelyVisibleItemPosition()
            if ((visibleItemCount + pastVisibleItems) >= recyclerView.adapter.itemCount - threshold) {
                thresholdReached()
            }
        }
    }
}

/**
 * Extension function for [ViewPager] to listen for changes in the page selected an receive a notification
 * with the position of the currently selected position.
 */
fun ViewPager.pageChangeUpdate(onPageChanged: (Int) -> Unit) {

    addOnPageChangeListener(ViewPagerPageChangeListener(onPageChanged))

}

/**
 * [ViewPager.OnPageChangeListener] that executes [onPageChanged] when a new page is selected
 */
private class ViewPagerPageChangeListener(val onPageChanged: (Int) -> Unit) : ViewPager.OnPageChangeListener {


    override fun onPageScrollStateChanged(state: Int) {
        // no op
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        // no op
    }

    override fun onPageSelected(position: Int) {
        onPageChanged(position)
    }
}

/**
 * Extension function for [ViewPager] to set the current item in a delayed way.
 * The reason for this code is that if we try to call setCurrentItem in a ViewPager that
 * has not been renderer, we never get the proper positioning.
 * This way, we make sure to set the current item once that the ViewPager has been rendered.
 */
fun ViewPager.setCurrentItemDelayed(currentItem: Int) {
    post { setCurrentItem(currentItem, false) }
}