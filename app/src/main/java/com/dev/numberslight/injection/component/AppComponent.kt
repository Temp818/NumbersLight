package com.dev.numberslight.injection.component

import com.dev.numberslight.detail.injection.DetailComponent
import com.dev.numberslight.injection.module.ApiModule
import com.dev.numberslight.list.injection.ListComponent
import dagger.Component
import dagger.Module

@Component(
    modules = [
        ApiModule::class,
    ]
)
interface AppComponent {
    fun detailComponent(): DetailComponent.Factory
    fun listComponent(): ListComponent.Factory
}

@Module(
    subcomponents = [
        DetailComponent::class,
        ListComponent::class
    ]
)
object SubcomponentsModule