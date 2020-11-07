package com.dev.numberslight.numbers.injection

import androidx.lifecycle.ViewModel
import com.dev.numberslight.injection.ViewModelKey
import com.dev.numberslight.numbers.viewmodel.NumbersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class NumbersModule {

    @Binds
    @IntoMap
    @ViewModelKey(NumbersViewModel::class)
    abstract fun bindViewModel(viewModel: NumbersViewModel): ViewModel
}