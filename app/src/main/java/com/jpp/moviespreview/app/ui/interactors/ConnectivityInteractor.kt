package com.jpp.moviespreview.app.ui.interactors

import android.content.Context
import com.jpp.moviespreview.app.util.extentions.isConnectedToNetwork

/**
 * Created by jpp on 10/14/17.
 */
interface ConnectivityInteractor {

    fun isConnectedToNetwork(): Boolean
}

class ConnectivityInteractorImpl(private val context: Context) : ConnectivityInteractor {
    override fun isConnectedToNetwork() = context.isConnectedToNetwork()
}