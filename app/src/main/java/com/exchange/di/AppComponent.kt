package com.exchange.di

import android.content.Context
import com.exchange.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        DataModule::class,
        UiModule::class]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}


