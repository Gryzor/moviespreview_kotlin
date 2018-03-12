package com.jpp.moviespreview.app.ui

import android.app.Activity
import com.jpp.moviespreview.app.mock
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify

/**
 * Created by jpp on 3/12/18.
 */
class FlowResolverTest {

    private lateinit var activity: Activity
    private lateinit var subject: FlowResolver

    @Before
    fun setUp() {
        activity = mock()
        subject = FlowResolverImpl(activity)
    }


    @Test
    fun goToMainScreen_finishesCurrentScreen() {
        subject.goToMainScreen()

        verify(activity).finish()
    }


}