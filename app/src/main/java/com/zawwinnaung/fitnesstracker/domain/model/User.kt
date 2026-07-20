package com.zawwinnaung.fitnesstracker.domain.model

data class User(
    val id: Int,
    val userName: String,
    val email: String,
    val dateOfBirth: String,
    val gender: String
) {
    val dobDisplay: String get() = dateOfBirth.ifBlank { "Not set" }
    val genderDisplay: String get() = gender.ifBlank { "Not set" }
}
