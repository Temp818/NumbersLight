package com.dev.numberslight.api

import com.dev.numberslight.api.response.NumberLightResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface NumbersLightService {
    @GET("test/json.php")
    suspend fun getNumbersLight(): List<NumberLightResponse>
}