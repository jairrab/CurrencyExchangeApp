package com.jairrab.conversionrateapp.di.modules

import com.jairrab.domain.repository.DomainRepository
import com.nhaarman.mockitokotlin2.mock
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object TestDataModule {

    @Provides
    @JvmStatic
    @Singleton
    fun provideDataRepository(): DomainRepository {
        return mock()
    }
}