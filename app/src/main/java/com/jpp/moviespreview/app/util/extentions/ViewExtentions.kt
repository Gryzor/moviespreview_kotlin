package com.jpp.moviespreview.app.util.extentions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

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