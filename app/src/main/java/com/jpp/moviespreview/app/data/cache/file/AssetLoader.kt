package com.jpp.moviespreview.app.data.cache.file

import android.content.Context
import com.google.gson.Gson
import com.jpp.moviespreview.app.data.Licenses
import com.jpp.moviespreview.app.util.extentions.fromJson
import java.io.IOException

/**
 * Utility class to load objects from the file system
 *
 * Created by jpp on 1/20/18.
 */
interface AssetLoader {
    /**
     * Loads the [Licenses] that are stored in the application assets folder.
     */
    fun loadLicences(): Licenses?
}

class AssetLoaderImpl(private val context: Context) : AssetLoader {

    private companion object {
        val LICENSES_FILE_LOCATION = "licences.json"
        val GSON_LOADER by lazy { Gson() }
    }


    override fun loadLicences(): Licenses? {
        val loadedLicences: Licenses?
        loadedLicences = try {
            val input = context.assets.open(LICENSES_FILE_LOCATION)
            val size = input.available()
            val buffer = ByteArray(size)
            input.read(buffer)
            input.close()
            GSON_LOADER.fromJson(String(buffer))
        } catch (e: IOException) {
            null
        }
        return loadedLicences
    }
}