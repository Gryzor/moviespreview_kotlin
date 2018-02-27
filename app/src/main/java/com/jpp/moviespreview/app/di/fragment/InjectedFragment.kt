package com.jpp.moviespreview.app.di.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.jpp.moviespreview.app.MoviesPreviewApp
import com.jpp.moviespreview.app.di.HasSubcomponentBuilders

/**
 * Base Fragment that is injected by the DI system
 *
 * Created by jpp on 2/21/18.
 */
abstract class InjectedFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupFragmentComponent()
    }

    protected fun setupFragmentComponent() {
        injectMembers(MoviesPreviewApp[activity])
    }

    protected abstract fun injectMembers(hasSubcomponentBuilders: HasSubcomponentBuilders)
}