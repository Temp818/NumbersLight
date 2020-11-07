package com.dev.numberslight.repository

import com.dev.numberslight.api.NumbersLightService
import com.dev.numberslight.mapper.NumberMapper
import com.dev.numberslight.model.NumberLight
import com.dev.numberslight.util.Resource
import javax.inject.Inject

class NumberRepositoryImpl @Inject constructor(
    private val mapper: NumberMapper,
    private val service: NumbersLightService
) : NumbersRepository {

    private var data: List<NumberLight>? = null

    override suspend fun getNumbersAsync(): Resource<List<NumberLight>> =
        try {
            if (data == null) {
                data = mapper.convertNumbersResponseToNumber(service.getNumbersLight())
            }
            Resource.Done(data!!)
        } catch (exception: Exception) {
            Resource.Error(exception, null)
        }
}