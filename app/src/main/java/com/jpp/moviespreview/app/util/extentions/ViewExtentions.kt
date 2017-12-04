package com.jpp.moviespreview.app.util.extentions

import android.content.Context
import android.graphics.Bitmap
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.jpp.moviespreview.R

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
fun ImageView.loadImageUrlWithCallback(imageUrl: String, callback: (Bitmap) -> Unit) {
    Glide.with(ctx)
            .load(imageUrl)
            .asBitmap()
            .into(CallbackTarget(this, callback))
}


/**
 * Inner callback class
 */
private class CallbackTarget(private val target: ImageView, private val callback: (Bitmap) -> Unit) : SimpleTarget<Bitmap>() {

    override fun onResourceReady(resource: Bitmap, glideAnimation: GlideAnimation<in Bitmap>) {
        target.setImageBitmap(resource)
        target.scaleType = ImageView.ScaleType.CENTER_CROP
        callback(resource)
    }

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