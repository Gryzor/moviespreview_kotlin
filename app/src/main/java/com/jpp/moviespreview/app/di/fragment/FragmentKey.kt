package com.jpp.moviespreview.app.di.fragment

import android.support.v4.app.Fragment
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Used for multibinding to construct maps.
 *
 * Created by jpp on 2/21/18.
 */
@MapKey
annotation class FragmentKey(val value: KClass<out Fragment>)