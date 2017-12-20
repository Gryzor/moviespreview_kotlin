package com.jpp.moviespreview.app.ui.detail

import android.content.res.Resources
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.ui.detail.body.MovieDetailsFragment

/**
 * Created by jpp on 12/13/17.
 */
class MovieDetailsViewPagerAdapter(fm: FragmentManager, private val resources: Resources) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return MovieDetailsFragment.newInstance()
    }

    override fun getCount() = 2

    override fun getPageTitle(position: Int): CharSequence = when (position) {
        0 -> resources.getString(R.string.movie_details_title)
        else -> resources.getString(R.string.movie_credits_title)
    }
}