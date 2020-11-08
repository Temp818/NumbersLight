package com.dev.numberslight.mapper

import com.dev.numberslight.api.response.DetailResponse
import com.dev.numberslight.api.response.NumberLightResponse
import com.dev.numberslight.model.Detail
import com.dev.numberslight.model.NumberLight
import javax.inject.Inject

class NumberMapper @Inject constructor() {
    fun convertNumbersResponseToNumber(numbersLightResponse: List<NumberLightResponse>) =
        numbersLightResponse.map {
            NumberLight(it.name, it.image)
        }

    fun convertDetailResponseToDetail(detailResponse: DetailResponse) = with(detailResponse) {
        Detail(name, text, image)
    }
}