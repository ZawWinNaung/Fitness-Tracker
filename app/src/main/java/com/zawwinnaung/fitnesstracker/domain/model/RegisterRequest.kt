package com.zawwinnaung.fitnesstracker.domain.model

data class RegisterRequest(
    val userName: String,
    val email: String,
    val password: String,
)