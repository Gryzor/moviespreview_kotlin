package com.jpp.moviespreview.app.domain.movie.credits

import com.jpp.moviespreview.app.domain.CastCharacter
import com.jpp.moviespreview.app.domain.CrewPerson
import com.jpp.moviespreview.app.domain.MovieCredits
import com.jpp.moviespreview.app.domain.movie.MovieDataMapper
import com.jpp.moviespreview.app.data.MovieCredits as DataMovieCredits
import com.jpp.moviespreview.app.data.CastCharacter as DataCastCharacter
import com.jpp.moviespreview.app.data.CrewPerson as DataCrewPerson

/**
 * Mapper used to map movie credit classes between data and domain layer.
 *
 * Created by jpp on 11/8/17.
 */
class CreditsDataMapper : MovieDataMapper() {


    /**
     * Converts a [DataMovieCredits] into a domain [MovieCredits]
     */
    fun convertDataMovieCreditsIntoDomainMovieCredits(dataMovieCredits: DataMovieCredits) = with(dataMovieCredits) {
        MovieCredits(id,
                cast.mapTo(arrayListOf()) { convertDataCastCharacterToDomainCastCharacter(it) },
                crew.mapTo(arrayListOf()) { convertDataCrewPersonIntoDomainCrewPerson(it) })
    }


    private fun convertDataCastCharacterToDomainCastCharacter(dataCastCharacter: DataCastCharacter) = with(dataCastCharacter) {
        CastCharacter(cast_id,
                character,
                credit_id,
                gender,
                name,
                order,
                profile_path)
    }

    private fun convertDataCrewPersonIntoDomainCrewPerson(dataCrewPerson: DataCrewPerson) = with(dataCrewPerson) {
        CrewPerson(credit_id,
                department,
                gender,
                id,
                job,
                name,
                profile_path)
    }

}