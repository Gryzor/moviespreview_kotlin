package com.jpp.moviespreview.app.di.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jpp.moviespreview.app.MoviesPreviewApp
import com.jpp.moviespreview.app.di.HasSubcomponentBuilders

/**
 * Base Activity that is injected by the DI system.
 *
 * Created by jpp on 2/14/18.
 */
abstract class InjectedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActivityComponent()
    }

    protected fun setupActivityComponent() {
        injectMembers(MoviesPreviewApp[this])
    }

    protected abstract fun injectMembers(hasSubcomponentBuilders: HasSubcomponentBuilders)
}