package com.jpp.moviespreview.app.ui.sections.main.movies

import android.util.Log
import com.jpp.moviespreview.app.di.HasSubcomponentBuilders
import com.jpp.moviespreview.app.di.fragment.InjectedFragment
import com.jpp.moviespreview.app.ui.sections.main.movies.di.MoviesFragmentComponent
import javax.inject.Inject

/**
 * Created by jpp on 2/21/18.
 */
class MoviesFragment : InjectedFragment() {


    companion object {
        val TAG = "MoviesFragment"
        // Factory method to follow the Fragment.newInstance() Android pattern
        fun newInstance() = MoviesFragment()
    }



    @Inject
    lateinit var moviesPresenter: MoviesPresenter


    override fun onResume() {
        super.onResume()
        Log.d("JPP", "Is " + (moviesPresenter != null) )
    }




    override fun injectMembers(hasSubcomponentBuilders: HasSubcomponentBuilders) {
        (hasSubcomponentBuilders.getFragmentComponentBuilder(MoviesFragment::class.java) as MoviesFragmentComponent.Builder)
                .fragmentModule(MoviesFragmentComponent.MoviesFragmentModule(this)).build().injectMembers(this)
    }
}
