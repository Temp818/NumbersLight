package com.dev.numberslight.repository

import com.dev.numberslight.api.NumbersLightService
import com.dev.numberslight.mapper.NumberMapper
import com.dev.numberslight.model.Detail
import com.dev.numberslight.model.NumberLight
import com.dev.numberslight.util.Resource
import javax.inject.Inject

class NumberRepositoryImpl @Inject constructor(
    private val mapper: NumberMapper,
    private val service: NumbersLightService
) : NumbersRepository, DetailRepository {

    private var numbers: List<NumberLight>? = null

    override suspend fun getNumbers(): Resource<List<NumberLight>> =
        try {
            if (numbers == null) {
                numbers = mapper.convertNumbersResponseToNumber(service.getNumbersLight())
            }
            Resource.Done(numbers!!)
        } catch (exception: Exception) {
            Resource.Error(exception, null)
        }

    override suspend fun getDetail(name: String): Resource<Detail> =
        try {
            val data = mapper.convertDetailResponseToDetail(service.getDetail(name))
            Resource.Done(data)
        } catch (exception: Exception) {
            Resource.Error(exception, null)
        }
}