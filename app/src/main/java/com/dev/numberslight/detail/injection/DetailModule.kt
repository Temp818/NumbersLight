package com.dev.numberslight.detail.injection

import androidx.lifecycle.ViewModel
import com.dev.numberslight.detail.viewmodel.DetailViewModel
import com.dev.numberslight.injection.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class DetailModule {

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindViewModel(viewModel: DetailViewModel): ViewModel
}