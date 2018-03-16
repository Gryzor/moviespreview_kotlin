package com.jpp.moviespreview.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.StrictMode
import android.support.v4.app.Fragment
import com.jpp.moviespreview.BuildConfig
import com.jpp.moviespreview.app.data.DataModule
import com.jpp.moviespreview.app.di.HasSubcomponentBuilders
import com.jpp.moviespreview.app.di.activity.ActivityComponentBuilder
import com.jpp.moviespreview.app.di.fragment.FragmentComponentBuilder
import com.jpp.moviespreview.app.domain.DomainModule
import com.jpp.moviespreview.app.ui.UiModule
import com.jpp.moviespreview.app.ui.sections.about.di.AboutComponent
import com.jpp.moviespreview.app.ui.sections.about.di.AboutModule
import javax.inject.Inject
import javax.inject.Provider

/**
 * Application class that injects the initial application scope graph
 *
 * Created by jpp on 10/4/17.
 */
open class MoviesPreviewApp : Application(), HasSubcomponentBuilders {


    @Inject
    lateinit var activityComponentBuilders: Map<Class<out Activity>, @JvmSuppressWildcards Provider<ActivityComponentBuilder<*, *>>>
    @Inject
    lateinit var fragmentComponentBuilders: Map<Class<out Fragment>, @JvmSuppressWildcards Provider<FragmentComponentBuilder<*, *>>>

    lateinit var appComponent: AppComponent


    override fun onCreate() {
        if (BuildConfig.DEBUG) {
            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .build())
        }


        super.onCreate()


        appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .dataModule(DataModule())
                .domainModule(DomainModule())
                .uiModule(UiModule())
                .build()
        appComponent.inject(this)
    }


    override fun getActivityComponentBuilder(activityClass: Class<out Activity>): ActivityComponentBuilder<*, *> =
            activityComponentBuilders[activityClass]!!.get()

    override fun getFragmentComponentBuilder(fragmentClass: Class<out Fragment>): FragmentComponentBuilder<*, *> =
            fragmentComponentBuilders[fragmentClass]!!.get()


    companion object {
        operator fun get(context: Context): HasSubcomponentBuilders =
                context.applicationContext as HasSubcomponentBuilders
    }

    open fun aboutComponent(): AboutComponent = appComponent.plus(AboutModule())

}