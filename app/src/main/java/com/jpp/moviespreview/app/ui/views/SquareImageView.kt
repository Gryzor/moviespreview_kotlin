package com.jpp.moviespreview.app.ui.views

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

/**
 * Created by jpp on 11/11/17.
 */
class SquareImageView : ImageView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = getMeasuredWidth()
        setMeasuredDimension(width, width)
    }
}