package com.jairrab.conversionrateapp.di.modules

import com.jairrab.remote.service.RetrofitService
import com.nhaarman.mockitokotlin2.mock
import dagger.Module
import dagger.Provides

@Module
object TestRemoteModule {

    @Provides
    @JvmStatic
    fun provideRetrofitTestClient(): RetrofitService {
        return mock()
    }
}