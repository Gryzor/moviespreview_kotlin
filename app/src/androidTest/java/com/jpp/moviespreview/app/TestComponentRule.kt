package com.jpp.moviespreview.app

import android.support.test.InstrumentationRegistry
import com.jpp.moviespreview.app.data.EspressoDataModule
import com.jpp.moviespreview.app.ui.EspressoUiModule
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * Created by jpp on 10/14/17.
 */
class TestComponentRule : TestRule {

    var testComponent: EspressoAppComponent? = null

    private fun setupDaggerTestComponentApplication() {
        val application = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as MoviesPreviewApp
        testComponent = DaggerEspressoAppComponent
                .builder()
                .appModule(AppModule(application))
                .espressoDataModule(EspressoDataModule())
                .espressoUiModule(EspressoUiModule())
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