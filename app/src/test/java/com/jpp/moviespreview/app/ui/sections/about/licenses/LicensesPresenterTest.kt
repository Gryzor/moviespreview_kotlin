package com.jpp.moviespreview.app.ui.sections.about.licenses

import com.jpp.moviespreview.app.BackgroundExecutorForTesting
import com.jpp.moviespreview.app.mock
import com.jpp.moviespreview.app.ui.License
import com.jpp.moviespreview.app.ui.MoviesContextHandler
import com.nhaarman.mockito_kotlin.*
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Created by jpp on 3/28/18.
 */
class LicensesPresenterTest {

    private lateinit var moviesContextHandler: MoviesContextHandler
    private lateinit var interactor: LicensesPresenterInteractor
    private lateinit var backgroundExecutor: BackgroundExecutorForTesting
    private lateinit var licencesFlowResolver: LicencesFlowResolver
    private lateinit var viewInstance: LicensesView
    private lateinit var subject: LicensesPresenter


    @Before
    fun setUp() {
        backgroundExecutor = BackgroundExecutorForTesting()

        moviesContextHandler = mock()
        interactor = mock()
        licencesFlowResolver = mock()
        viewInstance = mock()

        subject = LicensesPresenterImpl(moviesContextHandler, interactor, backgroundExecutor, licencesFlowResolver)
    }


    @Test
    fun linkViewShowLicencesWhenInContext() {
        val uiLicences: List<License> = mock()

        whenever(moviesContextHandler.getLicenses()).thenReturn(uiLicences)

        subject.linkView(viewInstance)

        verify(viewInstance).showLicences(uiLicences)
        verifyZeroInteractions(interactor)
    }


    @Test
    fun linkViewExecutesInteractorWhenNoLicencesInContext() {
        whenever(moviesContextHandler.getLicenses()).thenReturn(null)

        subject.linkView(viewInstance)

        verify(interactor).retrieveLicenses(any())
        assertTrue(backgroundExecutor.backgroundExecuted)
    }


    @Test
    fun presenterShowsDataWhenInteractorNotifies() {
        val uiLicences: List<License> = mock()

        whenever(moviesContextHandler.getLicenses()).thenReturn(null)

        doAnswer {
            val licensesData = it.arguments[0] as LicensesData
            licensesData.licences = uiLicences
        }.whenever(interactor).retrieveLicenses(any())

        subject.linkView(viewInstance)

        verify(moviesContextHandler).addLicenses(uiLicences)
        verify(viewInstance).showLicences(uiLicences)
    }

    @Test
    fun presenterShowsErrorWhenInteractorNotifies() {
        whenever(moviesContextHandler.getLicenses()).thenReturn(null)

        doAnswer {
            val licensesData = it.arguments[0] as LicensesData
            licensesData.error = mock() // type not important
        }.whenever(interactor).retrieveLicenses(any())

        subject.linkView(viewInstance)

        verify(viewInstance).showErrorLoadingLicenses()
    }
}