package com.jairrab.conversionrateapp

import android.app.Activity
import android.app.Application
import androidx.test.platform.app.InstrumentationRegistry
import com.jairrab.conversionrateapp.di.DaggerTestAppComponent
import com.jairrab.conversionrateapp.di.TestAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class TestApp : Application(), HasActivityInjector {

    @Inject lateinit var injector: DispatchingAndroidInjector<Activity>
    private lateinit var appComponent: TestAppComponent

    override fun activityInjector(): AndroidInjector<Activity> {
        return injector
    }

    companion object {
        fun appComponent(): TestAppComponent {
            return (InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
                    as TestApp).appComponent
        }
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerTestAppComponent.builder()
            .application(this)
            .build()
        appComponent.inject(this)
    }
}