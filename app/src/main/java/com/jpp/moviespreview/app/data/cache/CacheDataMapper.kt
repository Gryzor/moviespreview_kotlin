package com.jpp.moviespreview.app.data.cache

import com.jpp.moviespreview.app.data.ImagesConfiguration
import com.jpp.moviespreview.app.data.MoviesConfiguration
import com.jpp.moviespreview.app.data.cache.db.*
import com.jpp.moviespreview.app.util.extentions.addList
import com.jpp.moviespreview.app.data.CastCharacter as DataCastCharacter
import com.jpp.moviespreview.app.data.CrewPerson as DataCrewPerson
import com.jpp.moviespreview.app.data.Genre as DataGenre
import com.jpp.moviespreview.app.data.Movie as DataMovie
import com.jpp.moviespreview.app.data.MovieCredits as DataMovieCredits
import com.jpp.moviespreview.app.data.MoviePage as DataMoviePage


/**
 * Maps data classes between the cache and the data module
 *
 * Created by jpp on 10/20/17.
 */
class CacheDataMapper {

    private companion object {
        val IMAGE_TYPE_POSTER = 1
        val IMAGE_TYPE_PROFILE = 2
    }


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
    fun convertImagesConfigurationToCacheModel(parentId: Long, moviesConfiguration: MoviesConfiguration): List<ImageSize> = with(moviesConfiguration) {
        convertImageSizes(parentId, images.poster_sizes, IMAGE_TYPE_POSTER)
                .addList(convertImageSizes(parentId, images.profile_sizes, IMAGE_TYPE_PROFILE))
    }


    /**
     * Inner helper method
     */
    private fun convertImageSizes(parentId: Long, imageSizes: List<String>, imageType: Int): MutableList<ImageSize> {
        val dataImageSizes = ArrayList<ImageSize>()
        imageSizes.mapTo(dataImageSizes) { ImageSize(it, parentId, imageType) }
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
        val posterSizes = cacheImageSizes.mapNotNull { mapImageSizeTo(it, IMAGE_TYPE_POSTER) }
        val profileSizes = cacheImageSizes.mapNotNull { mapImageSizeTo(it, IMAGE_TYPE_PROFILE) }
        return ImagesConfiguration(baseUrl, posterSizes, profileSizes)
    }

    private fun mapImageSizeTo(imageSize: ImageSize, imageType: Int): String? {
        return if (imageSize.imageType == imageType) {
            imageSize.size
        } else {
            null
        }
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
                    posterPath ?: "empty",
                    backdropPath ?: "empty",
                    getGenreIdsFromDataGenres(cacheGenres),
                    voteCount,
                    voteAverage,
                    popularity)
        }
    }


    /**
     * Converts a list of [GenresByMovies] into the list of Int that represents each one of them
     */
    private fun getGenreIdsFromDataGenres(cacheGenres: List<GenresByMovies>) = cacheGenres.mapTo(ArrayList()) { it.genreId }


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
        return dataMovies.mapTo(ArrayList()) {
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
    }


    /***************************************
     ********** CREDITS SECTION ************
     ***************************************/

    /**
     * Converts a list of cache [CastCharacter] into a list of [DataCastCharacter]
     */
    fun convertCacheCharacterIntoDataCharacter(cacheCreditCharacters: List<CastCharacter>): List<DataCastCharacter> {
        return cacheCreditCharacters.mapTo(ArrayList()) {
            DataCastCharacter(it.id,
                    it.character,
                    it.creditId,
                    it.gender,
                    it.name,
                    it.order,
                    it.profilePath ?: "empty")
        }
    }


    /**
     * Converts a list of cache [CrewPerson] into a list of [DataCrewPerson]
     */
    fun convertCacheCrewIntoDataCrew(cacheCrew: List<CrewPerson>): List<DataCrewPerson> {
        return cacheCrew.mapTo(ArrayList()) {
            DataCrewPerson(it.creditId,
                    it.department,
                    it.gender, it.id,
                    it.job,
                    it.name,
                    it.profilePath ?: "empty")
        }
    }

    /**
     * Converts a list of [DataCastCharacter] into a list of cache [CastCharacter]
     */
    fun convertDataCharacterIntoCacheCharacter(dataCharacters: List<DataCastCharacter>, movieId: Double): List<CastCharacter> {
        return dataCharacters.mapTo(ArrayList()) {
            CastCharacter(it.cast_id,
                    it.character,
                    it.credit_id,
                    it.gender,
                    it.name,
                    it.order,
                    it.profile_path,
                    movieId)
        }
    }

    /**
     * Converts a list of [DataCrewPerson] into a list of cache [CrewPerson]
     */
    fun convertDataCrewIntoCacheCrew(dataCrew: List<DataCrewPerson>, movieId: Double): List<CrewPerson> {
        return dataCrew.mapTo(ArrayList()) {
            CrewPerson(it.id,
                    it.department,
                    it.gender,
                    it.credit_id,
                    it.job,
                    it.name,
                    it.profile_path,
                    movieId)
        }
    }

}