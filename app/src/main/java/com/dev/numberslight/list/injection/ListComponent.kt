package com.dev.numberslight.list.injection

import dagger.Subcomponent

@Subcomponent(modules = [ListModule::class])
interface ListComponent {
    @Subcomponent.Builder
    interface Factory {
        fun create(): ListComponent
    }
}
