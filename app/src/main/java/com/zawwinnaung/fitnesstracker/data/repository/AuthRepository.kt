package com.zawwinnaung.fitnesstracker.data.repository

import com.zawwinnaung.fitnesstracker.domain.model.RegisterRequest
import com.zawwinnaung.fitnesstracker.domain.model.User
import com.zawwinnaung.fitnesstracker.domain.result.NetworkResult

interface AuthRepository {
    suspend fun login(email: String, password: String): NetworkResult<User>

    suspend fun register(request: RegisterRequest): NetworkResult<Unit>

}