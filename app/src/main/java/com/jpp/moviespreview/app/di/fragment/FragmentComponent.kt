package com.jpp.moviespreview.app.di.fragment

import android.support.v4.app.Fragment
import dagger.MembersInjector

/**
 * Defines the signature of a [MembersInjector] for Fragments.
 *
 * Created by jpp on 2/21/18.
 */
interface FragmentComponent<F : Fragment> : MembersInjector<F>