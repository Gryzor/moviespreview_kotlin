package com.jpp.moviespreview.app.extentions

import android.app.Activity
import com.jpp.moviespreview.app.MoviesPreviewApp

/**
 * Created by jpp on 10/5/17.
 */

val Activity.app: MoviesPreviewApp
    get() = application as MoviesPreviewApp