package com.zawwinnaung.fitnesstracker.data.dto

data class LoginResponseDto(
    val status: String,
    val code: Int,
    val message: String,
    val data: UserDto?
)

data class UserDto(
    val user_id: Int,
    val first_name: String,
    val last_name: String,
    val email: String,
    val dob: String,
    val sex: String
)