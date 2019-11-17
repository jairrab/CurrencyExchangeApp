package com.jairrab.conversionrateapp.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.jairrab.presentation.ActivityViewModel
import com.jairrab.presentation.MainViewModel
import com.jairrab.conversionrateapp.di.factory.ViewModelFactory
import com.jairrab.conversionrateapp.di.factory.ViewModelKey
import com.jairrab.conversionrateapp.ui.mainactivity.MainActivity
import com.jairrab.conversionrateapp.ui.mainview.MainView
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

/**
 * Enable injecting dependencies to ViewModel Constructor
 * @see [https://stackoverflow.com/a/49087002/7960756](https://stackoverflow.com/a/49087002/7960756)
 */
@Module
internal abstract class ViewModelFactoryModule {
    /**
     * see https://medium.com/chili-labs/android-viewmodel-injection-with-dagger-f0061d3402ff
     * for reason behind Singleton scoping
     * Enable injecting dependencies to ViewModel Constructor
     * see https://stackoverflow.com/a/49087002/7960756
     */
    @Singleton
    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ActivityViewModel::class)
    abstract fun bindActivityViewModel(viewModel: ActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}

@Module
class InjectActivityViewModel {
    @Provides
    fun mapControllerViewModel(
        factory: ViewModelProvider.Factory,
        target: MainActivity
    ): ActivityViewModel {
        return ViewModelProviders.of(target, factory).get(ActivityViewModel::class.java)
    }
}

@Module
class InjectMainViewModel {
    @Provides
    fun mapControllerViewModel(
        factory: ViewModelProvider.Factory,
        target: MainView
    ): MainViewModel {
        return ViewModelProviders.of(target, factory).get(MainViewModel::class.java)
    }
}
