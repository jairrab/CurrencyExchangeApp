package com.jairrab.conversionrateapp.di.modules

import com.jairrab.conversionrateapp.scheduler.UiThread
import com.jairrab.conversionrateapp.ui.selectcurrencyview.SelectCurrencyView
import com.jairrab.conversionrateapp.ui.mainactivity.MainActivity
import com.jairrab.conversionrateapp.ui.mainview.MainView
import com.jairrab.domain.executor.PostExecutionThread
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UiModule {

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread

    @ContributesAndroidInjector(
        modules = [
            InjectActivityViewModel::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(
        modules = [
            InjectMainViewModel::class
        ]
    )
    abstract fun contributeMainView(): MainView

    @ContributesAndroidInjector
    abstract fun contributeCurrencySelectionView(): SelectCurrencyView
}