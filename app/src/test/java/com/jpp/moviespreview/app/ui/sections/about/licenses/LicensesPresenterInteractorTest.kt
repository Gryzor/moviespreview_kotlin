package com.jpp.moviespreview.app.ui.sections.about.licenses

import com.jpp.moviespreview.app.domain.Command
import com.jpp.moviespreview.app.domain.CommandData
import com.jpp.moviespreview.app.domain.Licenses
import com.jpp.moviespreview.app.mock
import com.jpp.moviespreview.app.ui.DomainToUiDataMapper
import com.jpp.moviespreview.app.ui.Error
import com.jpp.moviespreview.app.ui.License
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.anyOrNull
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import com.jpp.moviespreview.app.domain.Licenses as DomainLicences

/**
 * Created by jpp on 3/28/18.
 */
class LicensesPresenterInteractorTest {

    private lateinit var mapper: DomainToUiDataMapper
    private lateinit var retrieveLicencesCommand: Command<Any, Licenses>
    private lateinit var subject: LicensesPresenterInteractor


    @Before
    fun setUp() {
        mapper = mock()
        retrieveLicencesCommand = mock()
        subject = LicensesPresenterInteractorImpl(mapper, retrieveLicencesCommand)
    }


    @Test
    fun retrieveLicensesNotifiesSuccess() {
        val domainLicences: DomainLicences = mock()
        val uiLicences: List<License> = mock()
        val licensesData = LicensesData()

        whenever(mapper.convertDomainLicensesIntoUiLicenses(domainLicences)).thenReturn(uiLicences)


        doAnswer {
            val commandData = it.arguments[0] as CommandData<DomainLicences>
            commandData.value = domainLicences
        }.whenever(retrieveLicencesCommand).execute(any(), anyOrNull())


        subject.retrieveLicenses(licensesData)

        assertEquals(uiLicences, licensesData.licences)
        assertNull(licensesData.error)
    }


    @Test
    fun retrieveLicensesNotifiesError() {
        val licensesData = LicensesData()

        doAnswer {
            val commandData = it.arguments[0] as CommandData<DomainLicences>
            commandData.error = mock() // not important the type
        }.whenever(retrieveLicencesCommand).execute(any(), anyOrNull())


        subject.retrieveLicenses(licensesData)

        assertNull(licensesData.licences)
        assertNotNull(licensesData.error)
        assertEquals(licensesData.error!!.type, Error.UNKNOWN)

    }
}