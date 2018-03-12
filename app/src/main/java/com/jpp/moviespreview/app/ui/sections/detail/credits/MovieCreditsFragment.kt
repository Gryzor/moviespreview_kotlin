package com.jpp.moviespreview.app.ui.sections.detail.credits

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.di.HasSubcomponentBuilders
import com.jpp.moviespreview.app.di.fragment.InjectedFragment
import com.jpp.moviespreview.app.ui.CreditPerson
import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailCreditsPresenter
import com.jpp.moviespreview.app.ui.sections.detail.MovieDetailCreditsView
import com.jpp.moviespreview.app.ui.sections.detail.credits.di.MovieCreditsFragmentComponent
import com.jpp.moviespreview.app.util.extentions.ctx
import com.jpp.moviespreview.app.util.extentions.setVisible
import kotlinx.android.synthetic.main.movie_credits_fragment.*
import javax.inject.Inject

/**
 * Created by jpp on 12/20/17.
 */
class MovieCreditsFragment : InjectedFragment(), MovieDetailCreditsView {


    override fun injectMembers(hasSubcomponentBuilders: HasSubcomponentBuilders) {
        (hasSubcomponentBuilders.getFragmentComponentBuilder(MovieCreditsFragment::class.java) as MovieCreditsFragmentComponent.Builder)
                .fragmentModule(MovieCreditsFragmentComponent.MovieCreditsFragmentModule(this)).build().injectMembers(this)
    }

    companion object {
        // Factory method to follow the Fragment.newInstance() Android pattern
        fun newInstance() = MovieCreditsFragment()
    }

    @Inject
    lateinit var presenter: MovieDetailCreditsPresenter


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.linkView(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
            = inflater.inflate(R.layout.movie_credits_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(view.ctx)
        rv_movie_credits.layoutManager = layoutManager
        rv_movie_credits.addItemDecoration(DividerItemDecoration(view.ctx, layoutManager.orientation))
    }

    override fun showLoading() {
        loading_credits_view.show()
    }

    override fun showMovieCredits(credits: List<CreditPerson>) {
        loading_credits_view.hide()
        rv_movie_credits.adapter = MovieCreditsAdapter(credits)
    }

    override fun showErrorRetrievingCredits() {
        loading_credits_view.hide()
        movie_credits_error_text_view.setVisible()
    }

    override fun getTargetProfileImageHeight() = resources.getDimensionPixelSize(R.dimen.credits_list_item_image_view_height)

}