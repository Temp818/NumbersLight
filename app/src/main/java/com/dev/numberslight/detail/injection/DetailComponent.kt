package com.dev.numberslight.detail.injection

import com.dev.numberslight.detail.DetailFragment
import dagger.Subcomponent

@Subcomponent(modules = [DetailModule::class])
interface DetailComponent {
    @Subcomponent.Builder
    interface Factory {
        fun create(): DetailComponent
    }

    fun inject(detailFragment: DetailFragment)
}
