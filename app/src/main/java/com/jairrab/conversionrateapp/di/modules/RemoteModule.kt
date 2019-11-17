package com.jairrab.conversionrateapp.di.modules

import android.app.Application
import android.content.SharedPreferences
import com.jairrab.conversionrateapp.BuildConfig
import com.jairrab.conversionrateapp.ui.utils.Connectivity
import com.jairrab.data.repository.RemoteRepository
import com.jairrab.presentation.utils.PreferenceKeys.API_KEY
import com.jairrab.remote.RetrofitClient
import com.jairrab.remote.service.NetworkUtils
import com.jairrab.remote.service.RetrofitFactory
import com.jairrab.remote.service.RetrofitService
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RemoteModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideRetrofitService(
            sharedPreferences: SharedPreferences,
            networkUtils: NetworkUtils,
            application: Application
        ): RetrofitService {

            return RetrofitFactory.makeRetrofitTestClient(
                apiKey = sharedPreferences.getString(API_KEY, null)
                         ?: "80d387989056df5129d26447a3918039",
                isDebug = BuildConfig.DEBUG,
                networkUtils = networkUtils,
                cacheDir = application.cacheDir
            )
        }

        @Provides
        @JvmStatic
        fun provideNetworkUtils(connectivity: Connectivity): NetworkUtils {
            return object : NetworkUtils {
                override fun hasConnection(): Boolean {
                    return connectivity.isConnected
                }
            }
        }
    }

    @Binds
    abstract fun bindProjectsRemote(retrofitClient: RetrofitClient): RemoteRepository
}