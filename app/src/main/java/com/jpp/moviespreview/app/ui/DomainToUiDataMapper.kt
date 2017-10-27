package com.jpp.moviespreview.app.ui

import com.jpp.moviespreview.app.util.extentions.filterInList
import com.jpp.moviespreview.app.util.extentions.transformToInt
import com.jpp.moviespreview.app.domain.Genre as DomainGenre
import com.jpp.moviespreview.app.domain.Movie as DomainMovie
import com.jpp.moviespreview.app.domain.MoviePage as DomainMoviePage
import com.jpp.moviespreview.app.domain.MoviesConfiguration as DomainMovieConfiguration

/**
 * Maps domain model to UI model
 * Created by jpp on 10/11/17.
 */

class DomainToUiDataMapper {

    private var domainGenres: List<DomainGenre>? = null


    /**
     * Converts a [DomainMovieConfiguration] into a list of [ImageConfiguration] containing the images base URL and the sizes provided
     * by the [domainMoviesConfiguration].
     */
    fun convertConfigurationToImagesConfiguration(domainMoviesConfiguration: DomainMovieConfiguration): List<ImageConfiguration> {
        return domainMoviesConfiguration.posterImagesConfiguration.sizes.map {
            ImageConfiguration(domainMoviesConfiguration.posterImagesConfiguration.baseUrl, it, it.transformToInt())
        }
    }


    /**
     * Converts a list of [DomainGenre]s into a list of [MovieGenre]
     */
    fun convertDomainGenresIntoUiGenres(domainGenres: List<DomainGenre>): List<MovieGenre> {
        return domainGenres.map {
            MovieGenre(it.id, it.name)
        }
    }


    /**
     * Converts a [DomainMoviePage] into a [MoviePage] (UI model).
     * The [selectedImageConfiguration] is the [ImageConfiguration] used to configure the [Movie]'s images URL.
     * The [genres] are the ones used to set the proper [MovieGenre] into the [Movie]s.
     */
    fun convertDomainMoviePageToUiMoviePage(domainMoviePage: DomainMoviePage, selectedImageConfiguration: ImageConfiguration, genres: List<MovieGenre>) = with(domainMoviePage) {
        MoviePage(page,
                convertDomainMoviesInUiMovies(results, selectedImageConfiguration, genres),
                totalPages,
                totalResults)
    }


    /**
     * Converts a list of [DomainMovie] into a list of [Movie]s (UI model)
     */
    private fun convertDomainMoviesInUiMovies(domainMovies: List<DomainMovie>, selectedImageConfiguration: ImageConfiguration, genres: List<MovieGenre>): List<Movie> {
        return domainMovies.map {
            Movie(it.id,
                    it.title,
                    it.originalTitle,
                    it.overview,
                    it.releaseDate,
                    it.originalLanguage,
                    selectedImageConfiguration.prepareImageUrl(it.posterPath),
                    selectedImageConfiguration.prepareImageUrl(it.backdropPath),
                    getMappedUiGenres(it.genres, genres),
                    it.voteCount,
                    it.voteAverage,
                    it.popularity)
        }
    }


    /**
     * Maps the received [domainGenres] into a list of the same [MovieGenre]s
     */
    private fun getMappedUiGenres(domainGenres: List<DomainGenre>, genres: List<MovieGenre>)
            = genres.filterInList(domainGenres, { x: MovieGenre, y: DomainGenre -> x.id == y.id })


    /**
     * Converts a list of [MovieGenre] (UI model) into a list of [DomainGenre]
     */
    fun convertUiGenresToDomainGenres(uiGenres: List<MovieGenre>): List<DomainGenre> {
        if (domainGenres?.size == uiGenres.size) {
            return domainGenres as List<DomainGenre>
        }

        return uiGenres.map {
            DomainGenre(it.id, it.name)
        }
    }


}
