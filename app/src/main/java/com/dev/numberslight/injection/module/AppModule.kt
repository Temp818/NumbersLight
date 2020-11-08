package com.dev.numberslight.injection.module

import com.dev.numberslight.detail.repository.DetailRepository
import com.dev.numberslight.numbers.repository.NumbersRepository
import com.dev.numberslight.repository.NumberRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun bindNumbersRepository(repository: NumberRepositoryImpl): NumbersRepository

    @Binds
    abstract fun bindDetailRepository(repository: NumberRepositoryImpl): DetailRepository
}
