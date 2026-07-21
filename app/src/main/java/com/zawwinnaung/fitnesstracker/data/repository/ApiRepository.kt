package com.zawwinnaung.fitnesstracker.data.repository

import com.zawwinnaung.fitnesstracker.domain.model.RegisterRequest
import com.zawwinnaung.fitnesstracker.domain.model.User
import com.zawwinnaung.fitnesstracker.domain.result.NetworkResult

interface ApiRepository {
    suspend fun login(email: String, password: String): NetworkResult<User>

    suspend fun register(request: RegisterRequest): NetworkResult<Unit>

    suspend fun updateProfile(userId: Int, dob: String, sex: String): NetworkResult<User>
}