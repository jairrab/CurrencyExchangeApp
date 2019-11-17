package com.jairrab.conversionrateapp.di

import android.app.Application
import com.jairrab.conversionrateapp.MyApp
import com.jairrab.conversionrateapp.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        UiModule::class,
        ViewModelFactoryModule::class,
        DataModule::class,
        CacheModule::class,
        RemoteModule::class
    ]
)
interface AppComponent : AndroidInjector<MyApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}