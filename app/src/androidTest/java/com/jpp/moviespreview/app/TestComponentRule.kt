package com.jpp.moviespreview.app

import android.support.test.InstrumentationRegistry
import com.jpp.moviespreview.app.data.EspressoDataModule
import com.jpp.moviespreview.app.domain.EspressoDomainModule
import com.jpp.moviespreview.app.ui.EspressoUiModule
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * TestRule to inject the mocked dependencies to execute a test.
 *
 * Created by jpp on 10/14/17.
 */
@Deprecated("Remove this once that all EspressoTest are modified to use multibinding injection")
class TestComponentRule : TestRule {

    var testComponent: EspressoAppComponent? = null

    private fun setupDaggerTestComponentApplication() {
        val application = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as MoviesPreviewApp
        testComponent = DaggerEspressoAppComponent
                .builder()
                .espressoAppModule(EspressoAppModule(application))
                .espressoDataModule(EspressoDataModule())
                .espressoUiModule(EspressoUiModule())
                .espressoDomainModule(EspressoDomainModule())
                .build()
        application.appComponent = testComponent as AppComponent
    }


    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                try {
                    setupDaggerTestComponentApplication()
                    base.evaluate()
                } finally {
                    testComponent = null
                }
            }
        }
    }

}