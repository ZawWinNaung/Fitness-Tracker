package com.zawwinnaung.fitnesstracker.domain.model

data class User(
    val id: Int,
    val userName: String,
    val email: String,
    val dateOfBirth: String,
    val gender: String
) {
    val dobDisplay: String get() = dateOfBirth.ifBlank { "Not set" }
    val genderDisplay: String
        get() = when (gender) {
            "M" -> "Male"
            "F" -> "Female"
            "O" -> "Other"
            else -> "Not set"
        }
}
