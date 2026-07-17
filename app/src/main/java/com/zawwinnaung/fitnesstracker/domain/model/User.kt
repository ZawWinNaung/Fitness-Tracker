package com.zawwinnaung.fitnesstracker.domain.model

data class User(
    val id: Int,
    val fullName: String,
    val email: String,
    val dateOfBirth: String,
    val gender: String
)
