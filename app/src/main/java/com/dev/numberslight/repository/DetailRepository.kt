package com.dev.numberslight.repository

import com.dev.numberslight.model.Detail
import com.dev.numberslight.model.NumberLight
import com.dev.numberslight.util.Resource

interface DetailRepository {
    suspend fun getDetail(name: String): Resource<Detail>
}