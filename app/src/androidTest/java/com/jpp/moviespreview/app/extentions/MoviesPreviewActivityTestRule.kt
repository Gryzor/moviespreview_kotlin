package com.jpp.moviespreview.app.extentions

import android.app.Activity
import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.intercepting.SingleActivityFactory
import com.google.gson.Gson
import com.jpp.moviespreview.app.data.Genres
import com.jpp.moviespreview.app.domain.Genre
import com.jpp.moviespreview.app.domain.MoviesConfiguration
import com.jpp.moviespreview.app.domain.configuration.ConfigurationDataMapper
import com.jpp.moviespreview.app.domain.genre.GenreDataMapper
import com.jpp.moviespreview.app.fromJson

/**
 * Helper [ActivityTestRule] to specific usages.
 *
 * Created by jpp on 12/26/17.
 */
class MoviesPreviewActivityTestRule<T : Activity> : ActivityTestRule<T> {


    constructor(activityClass: Class<T>) : super(activityClass)

    constructor(activityClass: Class<T>, initialTouchMode: Boolean) : super(activityClass, initialTouchMode)

    constructor(activityClass: Class<T>, initialTouchMode: Boolean, launchActivity: Boolean) : super(activityClass, initialTouchMode, launchActivity)

    constructor(activityFactory: SingleActivityFactory<T>, initialTouchMode: Boolean, launchActivity: Boolean) : super(activityFactory, initialTouchMode, launchActivity)

    constructor(activityClass: Class<T>, targetPackage: String, launchFlags: Int, initialTouchMode: Boolean, launchActivity: Boolean) : super(activityClass, targetPackage, launchFlags, initialTouchMode, launchActivity)


    fun loadDomainConfig(): MoviesConfiguration
            = ConfigurationDataMapper().convertMoviesConfigurationFromDataModel(loadObjectFromJsonFile("data_movies_configuration.json"))

    fun loadDomainGenres(): List<Genre> = GenreDataMapper().convertGenreListFromDataModel(loadDataGenres().genres)

    fun loadDataGenres(): Genres = loadObjectFromJsonFile("data_genres.json")


    /**
     * Loads an object from the JSON file indicated in the [jsonFile] parameter.
     */
    inline private fun <reified R> loadObjectFromJsonFile(jsonFile: String): R {
        val input = InstrumentationRegistry.getInstrumentation().context.assets.open(jsonFile)
        val size = input.available()
        val buffer = ByteArray(size)
        input.read(buffer)
        input.close()
        return Gson().fromJson(String(buffer))
    }
}




