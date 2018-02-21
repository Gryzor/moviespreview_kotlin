package com.jpp.moviespreview.app.ui.interactors

import com.jpp.moviespreview.app.ui.ImageConfiguration
import com.jpp.moviespreview.app.ui.PosterImageConfiguration
import com.jpp.moviespreview.app.ui.ProfileImageConfiguration
import com.jpp.moviespreview.app.util.extentions.transformToInt

/**
 * Manages the images configurations for the provided properties.
 *
 * Created by jpp on 1/9/18.
 */
interface ImageConfigurationManager {


    /**
     * Finds a suitable [ProfileImageConfiguration] for the provided [height].
     * If none is appropriate, the last [ProfileImageConfiguration] in the [profileImageConfigs]
     * list is returned.
     */
    fun findProfileImageConfigurationForHeight(profileImageConfigs: List<ProfileImageConfiguration>,
                                               height: Int): ProfileImageConfiguration

    /**
     * Finds a suitable [PosterImageConfiguration] for the provided [width].
     * If none is appropriate, the last [PosterImageConfiguration] in the [posterImageConfigs] is
     * returned.
     */
    fun findPosterImageConfigurationForWidth(posterImageConfigs: List<PosterImageConfiguration>,
                                             width: Int): PosterImageConfiguration
}


class ImageConfigurationManagerImpl : ImageConfigurationManager {

    private var selectedPosterImageConfig: PosterImageConfiguration? = null
    private var selectedProfileImageConfig: ProfileImageConfiguration? = null


    override fun findPosterImageConfigurationForWidth(posterImageConfigs: List<PosterImageConfiguration>, width: Int): PosterImageConfiguration {
        if (selectedPosterImageConfig == null) {
            selectedPosterImageConfig = posterImageConfigs.firstOrNull {
                isImageConfigForSize(width, it)
            } ?: posterImageConfigs.last()
        }
        return selectedPosterImageConfig!!
    }

    override fun findProfileImageConfigurationForHeight(profileImageConfigs: List<ProfileImageConfiguration>, height: Int): ProfileImageConfiguration {

        if (selectedProfileImageConfig == null) {
            selectedProfileImageConfig = profileImageConfigs.firstOrNull {
                isImageConfigForSize(height, it)
            } ?: profileImageConfigs.last()
        }

        return selectedProfileImageConfig!!
    }

    private fun isImageConfigForSize(sizeToMatch: Int, profileImageConfiguration: ImageConfiguration): Boolean {
        with(profileImageConfiguration) {
            val realSize = size.transformToInt()
            return realSize != null && realSize > sizeToMatch
        }
    }
}