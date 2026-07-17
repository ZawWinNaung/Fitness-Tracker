package com.zawwinnaung.fitnesstracker.domain.result

sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T, val message: String) : NetworkResult<T>()
    data class Error(val message: String) : NetworkResult<Nothing>()
}