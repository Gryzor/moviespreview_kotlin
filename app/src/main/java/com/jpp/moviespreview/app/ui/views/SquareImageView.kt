package com.jpp.moviespreview.app.ui.views

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

/**
 * [ImageView] implementation that draws itself always as a square. It takes the width or the
 * height as the size to draw, depending on which one is bigger.
 *
 * Created by jpp on 1/5/18.
 */

class SquareImageView : ImageView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = measuredWidth
        val height = measuredHeight

        if (width >= height) {
            setMeasuredDimension(width, width)
        } else {
            setMeasuredDimension(height, height)
        }
    }
}
