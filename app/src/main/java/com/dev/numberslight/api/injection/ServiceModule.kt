package com.dev.numberslight.api.injection

import com.dev.numberslight.api.NumbersLightService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ServiceModule {

    @Provides
    fun provideNumbersLightService(retrofit: Retrofit) =
        retrofit.create(NumbersLightService::class.java)
}