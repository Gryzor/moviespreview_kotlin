package com.jpp.moviespreview.app.di

import android.app.Activity
import dagger.MembersInjector

/**
 * Created by jpp on 2/14/18.
 */
interface ActivityComponent<A : Activity> : MembersInjector<A>