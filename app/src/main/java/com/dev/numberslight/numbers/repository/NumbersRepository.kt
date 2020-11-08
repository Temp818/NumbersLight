package com.dev.numberslight.numbers.repository

import com.dev.numberslight.model.NumberLight
import com.dev.numberslight.util.Resource

interface NumbersRepository {
    suspend fun getNumbers(): Resource<List<NumberLight>>
}