package com.dev.numberslight.detail.injection

import dagger.Subcomponent

@Subcomponent(modules = [DetailModule::class])
interface DetailComponent {
    @Subcomponent.Builder
    interface Factory {
        fun create(): DetailComponent
    }
}
