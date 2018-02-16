package com.jpp.moviespreview.app

import android.app.Application
import android.content.Context
import android.support.test.runner.AndroidJUnitRunner

/**
 * Custom [AndroidJUnitRunner] implementation to provide [EspressoMoviesPreviewApp] as
 * Application for Espresso tests.
 *
 * Created by jpp on 2/16/18.
 */
class MoviesPreviewTestRunner : AndroidJUnitRunner() {

    @Throws(InstantiationException::class, IllegalAccessException::class, ClassNotFoundException::class)
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application =
            super.newApplication(cl, EspressoMoviesPreviewApp::class.java.name, context)
}
