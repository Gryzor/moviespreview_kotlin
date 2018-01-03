package com.jpp.moviespreview.app.ui

import com.jpp.moviespreview.R
import com.jpp.moviespreview.app.domain.ImageConfiguration.Companion.POSTER
import com.jpp.moviespreview.app.domain.ImageConfiguration.Companion.PROFILE
import com.jpp.moviespreview.app.util.extentions.filterInList
import com.jpp.moviespreview.app.util.extentions.mapIf
import com.jpp.moviespreview.app.domain.CastCharacter as DomainCastCharacter
import com.jpp.moviespreview.app.domain.CrewPerson as DomainCrewPerson
import com.jpp.moviespreview.app.domain.Genre as DomainGenre
import com.jpp.moviespreview.app.domain.Movie as DomainMovie
import com.jpp.moviespreview.app.domain.MoviePage as DomainMoviePage
import com.jpp.moviespreview.app.domain.MoviesConfiguration as DomainMovieConfiguration

/**
 * Maps domain model to UI model
 * Created by jpp on 10/11/17.
 */

class DomainToUiDataMapper {


    private companion object {
        val ACTION_GENRE_ID = 28
        val ADVENTURE_GENRE_ID = 12
        val ANIMATION_GENRE_ID = 16
        val COMEDY_GENRE_ID = 35
        val CRIME_GENRE_ID = 80
        val DOCUMENTARY_GENRE_ID = 99
        val DRAMA_GENRE_ID = 18
        val FAMILY_GENRE_ID = 10751
        val FANTASY_GENRE_ID = 14
        val HISTORY_GENRE_ID = 36
        val HORROR_GENRE_ID = 27
        val MUSIC_GENRE_ID = 10402
        val MYSTERY_GENRE_ID = 9648
        val SCI_FY_GENRE_ID = 878
        val TV_MOVIE_GENRE_ID = 10770
        val THRILLER_GENRE_ID = 53
        val WAR_GENRE_ID = 10752
        val WESTERN_GENRE_ID = 37
    }


    fun convertPosterImageConfigurations(domainMoviesConfiguration: DomainMovieConfiguration): List<PosterImageConfiguration> {
        return domainMoviesConfiguration.imagesConfiguration.mapIf(
                { it.type == POSTER },
                { PosterImageConfiguration(it.baseUrl, it.size) }
        )
    }


    fun convertProfileImageConfigurations(domainMoviesConfiguration: DomainMovieConfiguration): List<ProfileImageConfiguration> {
        return domainMoviesConfiguration.imagesConfiguration.mapIf(
                { it.type == PROFILE },
                { ProfileImageConfiguration(it.baseUrl, it.size) }
        )
    }

    /**
     * Converts a list of [DomainGenre]s into a list of [MovieGenre]
     */
    fun convertDomainGenresIntoUiGenres(domainGenres: List<DomainGenre>): List<MovieGenre> {
        return domainGenres.map {
            MovieGenre(it.id, it.name, mapGenreToIcon(it))
        }
    }

    /**
     * Converts a list of [MovieGenre] into a list of [DomainGenre]
     */
    fun convertUiGenresIntoDomainGenres(uiGenres: List<MovieGenre>): List<DomainGenre> {
        return uiGenres.map {
            DomainGenre(it.id, it.name)
        }
    }


    /**
     * Maps all the known genres with a given icon.
     */
    private fun mapGenreToIcon(domainGenre: DomainGenre): Int {
        when (domainGenre.id) {
            ACTION_GENRE_ID -> return R.drawable.ic_action
            ADVENTURE_GENRE_ID -> return R.drawable.ic_adventure
            ANIMATION_GENRE_ID -> return R.drawable.ic_animation
            COMEDY_GENRE_ID -> return R.drawable.ic_comedy
            CRIME_GENRE_ID -> return R.drawable.ic_crime
            DOCUMENTARY_GENRE_ID -> return R.drawable.ic_documentary
            DRAMA_GENRE_ID -> return R.drawable.ic_drama
            FAMILY_GENRE_ID -> return R.drawable.ic_family
            FANTASY_GENRE_ID -> return R.drawable.ic_fantasy
            HISTORY_GENRE_ID -> return R.drawable.ic_history
            HORROR_GENRE_ID -> return R.drawable.ic_horror
            MUSIC_GENRE_ID -> return R.drawable.ic_music
            MYSTERY_GENRE_ID -> return R.drawable.ic_mystery
            SCI_FY_GENRE_ID -> return R.drawable.ic_science_ficcion
            TV_MOVIE_GENRE_ID -> return R.drawable.ic_tv_movie
            THRILLER_GENRE_ID -> return R.drawable.ic_thriller
            WAR_GENRE_ID -> return R.drawable.ic_war
            WESTERN_GENRE_ID -> return R.drawable.ic_western
            else -> return R.drawable.ic_generic
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
                    listOf(
                            selectedImageConfiguration.prepareImageUrl(it.posterPath),
                            selectedImageConfiguration.prepareImageUrl(it.backdropPath)
                    ),
                    getMappedUiGenres(it.genres, genres),
                    it.voteCount,
                    it.voteAverage,
                    it.popularity)
        }
    }


    /**
     * Converts a [Movie] into a [DomainMovie]
     */
    fun convertUiMovieIntoDomainMovie(movie: Movie): DomainMovie = with(movie) {
        return DomainMovie(id,
                title,
                originalTitle,
                overview,
                releaseDate,
                originalLanguage,
                images[0],
                images[1],
                convertUiGenresIntoDomainGenres(genres),
                voteCount,
                voteAverage,
                popularity)
    }


    /**
     * Maps the received [domainGenres] into a list of the same [MovieGenre]s
     */
    private fun getMappedUiGenres(domainGenres: List<DomainGenre>, genres: List<MovieGenre>)
            = genres.filterInList(domainGenres, { x: MovieGenre, y: DomainGenre -> x.id == y.id })


    /**
     * Converts a list of [MovieGenre] (UI model) into a list of [DomainGenre].
     */
    fun convertUiGenresToDomainGenres(uiGenres: List<MovieGenre>): List<DomainGenre> {
        return uiGenres.map {
            DomainGenre(it.id, it.name)
        }
    }


    /**
     * Converts the provided lists of [DomainCastCharacter] and [DomainCrewPerson] into
     * a single list of [CreditPerson]
     */
    fun convertDomainCreditsInUiCredits(cast: List<DomainCastCharacter>, crew: List<DomainCrewPerson>, selectedImageConfiguration: ImageConfiguration): List<CreditPerson> {
        val creditPersonList = ArrayList<CreditPerson>()
        cast.mapTo(creditPersonList) { CreditPerson(selectedImageConfiguration.prepareImageUrl(it.profilePath.toString()), it.character, it.name) }
        crew.mapTo(creditPersonList) { CreditPerson(selectedImageConfiguration.prepareImageUrl(it.profilePath.toString()), it.name, it.department) }
        return creditPersonList
    }
}
