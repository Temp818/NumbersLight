package com.dev.numberslight.injection.module

import com.dev.numberslight.repository.DetailRepository
import com.dev.numberslight.repository.NumberRepositoryImpl
import com.dev.numberslight.repository.NumbersRepository
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun bindNumbersRepository(repository: NumberRepositoryImpl): NumbersRepository

    @Binds
    abstract fun bindDetailRepository(repository: NumberRepositoryImpl): DetailRepository
}
