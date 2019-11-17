package com.jairrab.conversionrateapp.di.modules

import android.app.Application
import com.jairrab.cache.db.AppDatabase
import com.jairrab.data.repository.CacheRepository
import com.nhaarman.mockitokotlin2.mock
import dagger.Module
import dagger.Provides

@Module
object TestCacheModule {

    @Provides
    @JvmStatic
    fun providesDataBase(application: Application): AppDatabase {
        return AppDatabase.getInstance(application)
    }


    @Provides
    @JvmStatic
    fun provideCacheRepository(): CacheRepository {
        return mock()
    }
}