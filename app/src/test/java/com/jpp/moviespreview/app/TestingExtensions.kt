package com.jpp.moviespreview.app

import org.mockito.Mockito

/**
 * Created by jpp on 10/11/17.
 */
inline fun <reified T: Any> mock() = Mockito.mock(T::class.java)