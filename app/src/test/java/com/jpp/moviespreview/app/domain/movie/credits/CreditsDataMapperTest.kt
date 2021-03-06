package com.jpp.moviespreview.app.domain.movie.credits

import com.jpp.moviespreview.app.data.MovieCredits
import com.jpp.moviespreview.app.data.cache.MoviesCacheImplTest
import com.jpp.moviespreview.app.util.extension.fuzzyAssert
import com.jpp.moviespreview.app.util.extension.loadObjectFromJsonFile
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

/**
 * Created by jpp on 11/8/17.
 */
class CreditsDataMapperTest {

    @Test
    fun convertDataMovieCreditsIntoDomainMovieCredits() {
        //-- prepare
        val dataMovieCredit = loadObjectFromJsonFile<MovieCredits>(MoviesCacheImplTest::class.java.classLoader, "data_movie_credits_non_nulls.json")

        //-- execute
        val domainMovieCredit = CreditsDataMapper().convertDataMovieCreditsIntoDomainMovieCredits(dataMovieCredit)

        // -- verify
        assertNotNull(domainMovieCredit)
        fuzzyAssert(dataMovieCredit.id, domainMovieCredit.id)
        assertEquals(dataMovieCredit.cast.size, domainMovieCredit.cast.size)
        assertEquals(dataMovieCredit.crew.size, domainMovieCredit.crew.size)

    }

}