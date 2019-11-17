package com.jairrab.conversionrateapp.di.modules

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.jairrab.conversionrateapp.ui.utils.Toaster
import dagger.Module
import dagger.Provides

@Module
object AppModule {

    @JvmStatic
    @Provides
    fun providesToaster(application: Application): Toaster {
        return Toaster(application)
    }

    @JvmStatic
    @Provides
    fun providesSharedPreferences(application: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }
}
