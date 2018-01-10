package com.jpp.moviespreview.app.ui.sections.search

import com.jpp.moviespreview.app.domain.MultiSearchParam
import com.jpp.moviespreview.app.ui.MoviesContext

/**
 * Multi search is a complex feature. This is a particular context, that will be specific
 * for multi search feature and will survive to activity rotation.
 *
 * Created by jpp on 1/10/18.
 */
class MultiSearchContext(private val context: MoviesContext) {

    var onGoingQueryParam: MultiSearchParam? = null

    fun getPosterImageConfigs() = context.posterImageConfig

    fun getProfileImageConfig() = context.profileImageConfig
}