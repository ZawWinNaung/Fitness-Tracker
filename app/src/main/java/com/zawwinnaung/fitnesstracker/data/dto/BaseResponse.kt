package com.zawwinnaung.fitnesstracker.data.dto

data class BaseResponse<T>(
    val status: String,
    val code: Int,
    val message: String,
    val data: T? = null
)