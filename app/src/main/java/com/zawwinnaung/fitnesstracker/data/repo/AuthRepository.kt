package com.zawwinnaung.fitnesstracker.data.repo

import com.zawwinnaung.fitnesstracker.domain.model.User
import com.zawwinnaung.fitnesstracker.domain.result.NetworkResult

interface AuthRepository {
    suspend fun login(email: String, password: String): NetworkResult<User>
}