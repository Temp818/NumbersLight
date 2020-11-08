package com.dev.numberslight.detail.repository

import com.dev.numberslight.model.Detail
import com.dev.numberslight.model.NumberLight
import com.dev.numberslight.util.Resource

interface DetailRepository {
    suspend fun getDetail(name: String): Resource<Detail>
}