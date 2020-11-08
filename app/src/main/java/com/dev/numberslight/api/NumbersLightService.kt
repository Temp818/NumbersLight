package com.dev.numberslight.api

import com.dev.numberslight.api.response.DetailResponse
import com.dev.numberslight.api.response.NumberLightResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface NumbersLightService {
    @GET("test/json.php")
    suspend fun getNumbersLight(): List<NumberLightResponse>

    @GET("test/json.php")
    suspend fun getDetail(@Query("name") name: String): DetailResponse
}