package com.jpp.moviespreview.app.ui

/**
 * Represents the configuration of the images from the UI perspective.
 * The URL that references the location of the images.
 * A list of possible sizes to retrieve (the presenters and views are the ones
 * that take care of finding the proper size)
 */
data class ImageConfiguration(val baseUrl: String, val sizes: List<String>)