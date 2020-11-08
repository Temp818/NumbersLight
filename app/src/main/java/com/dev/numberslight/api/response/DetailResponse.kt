package com.dev.numberslight.api.response

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class DetailResponse(
    val name: String,
    val text: String,
    val image: String
)