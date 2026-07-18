package com.zawwinnaung.fitnesstracker.data.mapper

import com.zawwinnaung.fitnesstracker.data.dto.UserDto
import com.zawwinnaung.fitnesstracker.domain.model.User

fun UserDto.toDomain(): User {
    return User(
        id = this.user_id,
        userName = this.user_name,
        email = this.email,
        dateOfBirth = this.dob ?: "",
        gender = when (this.sex) {
            "M" -> "Male"
            "F" -> "Female"
            null -> ""
            else -> "Other"
        }
    )
}