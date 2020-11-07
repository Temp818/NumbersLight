package com.dev.numberslight.numbers.injection

import com.dev.numberslight.numbers.NumbersFragment
import dagger.Subcomponent

@Subcomponent(modules = [NumbersModule::class])
interface NumbersComponent {
    @Subcomponent.Builder
    interface Factory {
        fun create(): NumbersComponent
    }

    fun inject(numbersFragment: NumbersFragment)
}
