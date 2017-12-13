package com.jpp.moviespreview.app.ui.detail

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by jpp on 12/13/17.
 */
class MovieDetailsViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return MovieDetailsFragment()
    }

    override fun getCount() = 2

    override fun getPageTitle(position: Int): CharSequence {
        return "Page $position"
    }
}