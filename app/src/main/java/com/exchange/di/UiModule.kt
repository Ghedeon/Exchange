package com.exchange.di

import androidx.lifecycle.ViewModel
import com.exchange.ui.ExchangeFragment
import com.exchange.ui.ExchangeViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class UiModule {

    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    abstract fun exchangeFragment(): ExchangeFragment

    @Binds
    @IntoMap
    @ViewModelKey(ExchangeViewModel::class)
    abstract fun bindViewModel(viewModel: ExchangeViewModel): ViewModel
}
