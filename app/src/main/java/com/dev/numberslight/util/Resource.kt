package com.dev.numberslight.util

sealed class Resource<T> {
    data class Loading<T>(val data: T? = null) : Resource<T>()
    data class Done<T>(val data: T) : Resource<T>()
    data class Error<T>(val throwable: Throwable, val data: T? = null) : Resource<T>()
}