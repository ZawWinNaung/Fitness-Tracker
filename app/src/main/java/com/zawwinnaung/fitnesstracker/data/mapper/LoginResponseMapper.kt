package com.zawwinnaung.fitnesstracker.data.mapper

import com.zawwinnaung.fitnesstracker.data.dto.UserDto
import com.zawwinnaung.fitnesstracker.domain.model.User

fun UserDto.toDomain(): User {
    return User(
        id = this.user_id,
        fullName = "${this.first_name} ${this.last_name}",
        email = this.email,
        dateOfBirth = this.dob,
        gender = when (this.sex) {
            "M" -> "Male"
            "F" -> "Female"
            else -> "Other"
        }
    )
}