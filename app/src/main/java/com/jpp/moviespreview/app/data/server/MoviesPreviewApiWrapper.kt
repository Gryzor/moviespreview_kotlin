package com.jpp.moviespreview.app.data.server

import com.jpp.moviespreview.BuildConfig
import com.jpp.moviespreview.app.data.MoviesConfiguration
import com.jpp.moviespreview.app.extentions.AllOpen

/**
 * Created by jpp on 10/11/17.
 */
@AllOpen
class MoviesPreviewApiWrapper(private val apiInstance: MoviesPreviewApi) {

    fun getLastMovieConfiguration(): MoviesConfiguration = apiInstance.getLastConfiguration(BuildConfig.API_KEY).execute().body()


}