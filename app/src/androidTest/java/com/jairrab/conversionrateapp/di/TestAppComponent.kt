package com.jairrab.conversionrateapp.di

import android.app.Application
import com.jairrab.domain.repository.DomainRepository
import com.jairrab.conversionrateapp.TestApp
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
        TestDataModule::class,
        TestCacheModule::class,
        TestRemoteModule::class,
        ViewModelFactoryModule::class,
        UiModule::class
    ]
)
interface TestAppComponent : AndroidInjector<TestApp> {

    fun domainRepository(): DomainRepository

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): TestAppComponent
    }
}