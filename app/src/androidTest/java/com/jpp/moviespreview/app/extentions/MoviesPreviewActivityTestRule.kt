package com.jpp.moviespreview.app.extentions

import android.app.Activity
import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import com.google.gson.Gson
import com.jpp.moviespreview.app.data.Genres
import com.jpp.moviespreview.app.data.MoviePage
import com.jpp.moviespreview.app.data.MultiSearchPage
import com.jpp.moviespreview.app.domain.Genre
import com.jpp.moviespreview.app.domain.MoviesConfiguration
import com.jpp.moviespreview.app.domain.configuration.ConfigurationDataMapper
import com.jpp.moviespreview.app.domain.genre.GenreDataMapper
import com.jpp.moviespreview.app.domain.movie.MovieDataMapper
import com.jpp.moviespreview.app.domain.movie.credits.CreditsDataMapper
import com.jpp.moviespreview.app.domain.search.MultiSearchDataMapper
import com.jpp.moviespreview.app.fromJson
import com.jpp.moviespreview.app.data.MovieCredits as DataMovieCredits
import com.jpp.moviespreview.app.domain.MovieCredits as DomainMovieCredits
import com.jpp.moviespreview.app.domain.MoviePage as DomainMoviePage
import com.jpp.moviespreview.app.domain.MultiSearchPage as DomainMultiSearchPage

/**
 * Helper [ActivityTestRule] to specific usages.
 *
 * Created by jpp on 12/26/17.
 */
class MoviesPreviewActivityTestRule<T : Activity>(activityClass: Class<T>) : ActivityTestRule<T>(activityClass) {


    /**
     * Loads a DOMAIN [MoviesConfiguration]
     */
    fun loadDomainConfig(): MoviesConfiguration
            = ConfigurationDataMapper().convertMoviesConfigurationFromDataModel(loadObjectFromJsonFile("data_movies_configuration.json"))

    /**
     * Loads a DOMAIN [Genre] list
     */
    fun loadDomainGenres(): List<Genre> = GenreDataMapper().convertGenreListFromDataModel(loadDataGenres().genres)


    /**
     * Loads a DOMAIN [DomainMoviePage]
     */
    fun loadDomainPage(page: Int): DomainMoviePage = MovieDataMapper().convertDataMoviePageIntoDomainMoviePage(loadDataPage(page), loadDomainGenres())


    /**
     * Loads a DOMAIN [DomainMovieCredits]
     */
    fun loadDomainMovieCredits(): DomainMovieCredits = CreditsDataMapper().convertDataMovieCreditsIntoDomainMovieCredits(loadMovieCredits())

    /**
     * Loads a DOMAIN [DomainMultiSearchPage]
     */
    fun loadDomainMultiSearchResult(page: Int, query:String): DomainMultiSearchPage
            = MultiSearchDataMapper(MovieDataMapper()).convertDataSearchPageIntoDomainSearchResult(loadMultiSearchDataPage(page), query, loadDomainGenres())

    /**********************
     * INNER DATA HELPERS *
     **********************/

    private fun loadDataGenres(): Genres = loadObjectFromJsonFile("data_genres.json")


    private fun loadDataPage(page: Int): MoviePage = when (page) {
        1 -> loadObjectFromJsonFile("data_movie_page_1.json")
        2 -> loadObjectFromJsonFile("data_movie_page_2.json")
        3 -> loadObjectFromJsonFile("data_movie_page_3.json")
        else -> {
            throw RuntimeException("Unsupported page requested in test. Page { $page }")
        }
    }


    private fun loadMovieCredits(): DataMovieCredits = loadObjectFromJsonFile("data_movie_credits.json")

    private fun loadMultiSearchDataPage(page: Int): MultiSearchPage = when (page) {
        1 -> loadObjectFromJsonFile("data_multi_search_page_1.json")
        else -> throw RuntimeException("Unsupported page requested in test. Page { $page }")
    }


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




