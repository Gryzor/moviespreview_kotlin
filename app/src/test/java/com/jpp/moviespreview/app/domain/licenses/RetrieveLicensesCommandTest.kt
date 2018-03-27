package com.jpp.moviespreview.app.domain.licenses

import com.jpp.moviespreview.app.data.cache.file.AssetLoader
import com.jpp.moviespreview.app.domain.CommandData
import com.jpp.moviespreview.app.domain.Licenses
import com.jpp.moviespreview.app.mock
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import com.jpp.moviespreview.app.data.Licenses as DataLicenses

/**
 * Created by jpp on 3/27/18.
 */
class RetrieveLicensesCommandTest {

    private lateinit var assetLoader: AssetLoader
    private lateinit var mapper: LicensesDataMapper
    private lateinit var subject: RetrieveLicensesCommand


    @Before
    fun setUp() {
        assetLoader = mock()
        mapper = mock()
        subject = RetrieveLicensesCommand(assetLoader, mapper)
    }


    @Test
    fun executeRetrievesLicencesFromAssetLoader() {
        val dataLicenses: DataLicenses = mock()
        val domainLicenses: Licenses = mock()

        whenever(assetLoader.loadLicences()).thenReturn(dataLicenses)
        whenever(mapper.convertDataLicencesIntoDomainLicences(dataLicenses)).thenReturn(domainLicenses)

        val data = CommandData<Licenses>(
                {
                    // no op
                },
                {
                    fail()
                }
        )

        subject.execute(data)

        verify(assetLoader).loadLicences()
        verify(mapper).convertDataLicencesIntoDomainLicences(any())
        assertEquals(domainLicenses, data.value)
    }


    @Test
    fun executeWhenAssetLoaderFails() {
        whenever(assetLoader.loadLicences()).thenReturn(null)

        val data = CommandData<Licenses>(
                {
                    fail()
                },
                {
                    // no op
                }
        )

        subject.execute(data)

        assertNotNull(data.error)
        assertTrue(data.error is IllegalStateException)
    }

}