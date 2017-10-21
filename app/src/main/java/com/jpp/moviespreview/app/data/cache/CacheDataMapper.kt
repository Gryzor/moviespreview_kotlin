package com.jpp.moviespreview.app.data.cache

import com.jpp.moviespreview.app.data.ImagesConfiguration
import com.jpp.moviespreview.app.data.MoviesConfiguration
import com.jpp.moviespreview.app.data.cache.db.*
import com.jpp.moviespreview.app.data.Genre as DataGenre
import com.jpp.moviespreview.app.data.Movie as DataMovie
import com.jpp.moviespreview.app.data.MoviePage as DataMoviePage


/**
 * Maps data classes between the cache and the data module
 *
 * Created by jpp on 10/20/17.
 */
class CacheDataMapper {


    /*****************************************************
     ********** MOVIES CONFIGURATION SECTION *************
     *****************************************************/

    /**
     * Converts [MoviesConfiguration] from the data model into the [ImageConfig] cache data model.
     */
    fun convertMoviesConfigurationToCacheModel(moviesConfiguration: MoviesConfiguration) = with(moviesConfiguration) {
        ImageConfig(images.base_url)
    }


    /**
     * Converts from [MoviesConfiguration] into a list of [ImageSize]. The [parentId] param is used to
     * set the [ImageSize.id] param.
     */
    fun convertImagesConfigurationToCacheModel(parentId: Long, moviesConfiguration: MoviesConfiguration) = with(moviesConfiguration) {
        convertImageSizes(parentId, images.poster_sizes)
    }


    /**
     * Inner helper method
     */
    private fun convertImageSizes(parentId: Long, imageSizes: List<String>): List<ImageSize> {
        val dataImageSizes = ArrayList<ImageSize>()
        imageSizes.mapTo(dataImageSizes) { ImageSize(it, parentId) }
        return dataImageSizes
    }


    /**
     * Converts [ImageConfig] and [ImageSize] list into a [MoviesConfiguration] data class.
     */
    fun convertCacheImageConfigurationToDataMoviesConfiguration(cacheImageConfig: ImageConfig, cacheImageSizes: List<ImageSize>): MoviesConfiguration =
            MoviesConfiguration(convertCacheImageSizeToImageDataConfiguration(cacheImageConfig.baseUrl, cacheImageSizes))


    /**
     * Inner helper method
     */
    private fun convertCacheImageSizeToImageDataConfiguration(baseUrl: String, cacheImageSizes: List<ImageSize>): ImagesConfiguration {
        val posterSizes = ArrayList<String>()
        cacheImageSizes.mapTo(posterSizes) { it.size }
        return ImagesConfiguration(baseUrl, posterSizes)
    }


    /*********************************************
     ********** MOVIES GENRE SECTION *************
     *********************************************/


    /**
     * Converts from [Genre] database model into data model [DataGenre]
     */
    fun convertCacheGenresIntoDataGenres(cacheGenres: List<Genre>): List<DataGenre> {
        return cacheGenres.mapTo(ArrayList()) {
            DataGenre(it.id, it.name)
        }
    }

    /**
     * Converts from [DataGenre] data model into [Genre] database model
     */
    fun convertDataGenresIntoCacheGenres(dataGenres: List<DataGenre>): List<Genre> {
        return dataGenres.mapTo(ArrayList()) {
            Genre(it.id, it.name)
        }
    }


    /***************************************
     ********** MOVIES SECTION *************
     ***************************************/


    /**
     * Converts a [MoviePage] into a [DataMoviePage]
     */
    fun convertCacheMoviePageInDataMoviePage(cacheMoviePage: MoviePage, dataMovies: List<DataMovie>) = with(cacheMoviePage) {
        DataMoviePage(page,
                dataMovies,
                totalPages,
                totalResults)
    }


    /**
     * Converts a cache [Movie] into a [DataMovie]
     */
    fun convertCacheMovieInDataMovie(cacheMovie: Movie, cacheGenres: List<GenresByMovies>?) = with(cacheMovie) {
        if (cacheGenres == null) {
            null
        } else {
            DataMovie(id,
                    title,
                    originalTile,
                    overview,
                    releaseDate,
                    originalLanguage,
                    posterPath,
                    backdropPath,
                    getGenreIdsFromDataGenres(cacheGenres),
                    voteCount,
                    voteAverage,
                    popularity)
        }
    }


    /**
     * Converts a list of [GenresByMovies] into the list of Int that represents each one of them
     */
    private fun getGenreIdsFromDataGenres(cacheGenres: List<GenresByMovies>): List<Int> {
        val genresId = ArrayList<Int>()
        cacheGenres.mapTo(genresId) { it.genreId }
        return genresId
    }


    /**
     * Converts a [DataMoviePage] into a [MoviePage]
     */
    fun convertDataMoviesPageIntoCacheMoviePage(dataMoviePage: DataMoviePage) = with(dataMoviePage) {
        MoviePage(page, total_pages, total_results)
    }


    /**
     * Converts from [DataMovie] into [Movie]
     */
    fun convertDataMoviesIntoCacheMovie(dataMovies: List<DataMovie>, dataMoviePage: DataMoviePage): List<Movie> {
        val dbMovies = ArrayList<Movie>()
        dataMovies.mapTo(dbMovies) {
            Movie(it.id,
                    it.title,
                    it.original_title,
                    it.overview,
                    it.release_date,
                    it.original_language,
                    it.poster_path,
                    it.backdrop_path,
                    it.vote_count,
                    it.vote_average,
                    it.popularity,
                    dataMoviePage.page
            )
        }
        return dbMovies
    }
}