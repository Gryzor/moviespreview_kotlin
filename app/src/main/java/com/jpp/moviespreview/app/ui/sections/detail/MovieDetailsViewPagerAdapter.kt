package com.jpp.moviespreview.app.ui.sections.detail

import android.content.res.Resources
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.ui.sections.detail.body.MovieDetailsFragment
import com.jpp.moviespreview.app.ui.sections.detail.credits.MovieCreditsFragment

/**
 * Created by jpp on 12/13/17.
 */
class MovieDetailsViewPagerAdapter(fm: FragmentManager, private val resources: Resources) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment = when (position) {
        0 -> MovieDetailsFragment.newInstance()
        else -> MovieCreditsFragment.newInstance()
    }

    override fun getCount() = 2

    override fun getPageTitle(position: Int): CharSequence = when (position) {
        0 -> resources.getString(R.string.movie_details_title)
        else -> resources.getString(R.string.movie_credits_title)
    }
}