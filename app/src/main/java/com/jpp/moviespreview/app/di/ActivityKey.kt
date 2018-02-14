package com.jpp.moviespreview.app.di

import android.app.Activity
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Used for multibinding to construct maps.
 *
 * Created by jpp on 2/14/18.
 */
@MapKey
annotation class ActivityKey(val value: KClass<out Activity>)