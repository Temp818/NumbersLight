package com.dev.numberslight.repository

import com.dev.numberslight.model.NumberLight
import com.dev.numberslight.util.Resource

interface NumbersRepository {
    suspend fun getNumbersAsync(): Resource<List<NumberLight>>
}