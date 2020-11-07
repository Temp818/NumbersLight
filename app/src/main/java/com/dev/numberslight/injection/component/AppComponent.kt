package com.dev.numberslight.injection.component

import com.dev.numberslight.api.injection.ServiceModule
import com.dev.numberslight.detail.injection.DetailComponent
import com.dev.numberslight.injection.ViewModelBuilderModule
import com.dev.numberslight.injection.module.ApiModule
import com.dev.numberslight.injection.module.AppModule
import com.dev.numberslight.numbers.injection.NumbersComponent
import dagger.Component
import dagger.Module

@Component(
    modules = [
        ApiModule::class,
        ServiceModule::class,
        SubcomponentsModule::class,
        ViewModelBuilderModule::class,
        AppModule::class
    ]
)
interface AppComponent {
    fun detailComponent(): DetailComponent.Factory
    fun listComponent(): NumbersComponent.Factory
}

@Module(
    subcomponents = [
        DetailComponent::class,
        NumbersComponent::class
    ]
)
object SubcomponentsModule