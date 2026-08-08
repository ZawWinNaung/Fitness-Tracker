package com.zawwinnaung.fitnesstracker.data.dto

data class UserDto(
    val user_id: Int,
    val user_name: String,
    val email: String,
    val dob: String?,
    val sex: String?
)