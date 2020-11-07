package com.dev.numberslight.api.response

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class NumberLightResponse(
    val name: String,
    val image: String
)