package com.zawwinnaung.fitnesstracker.data.repo

import com.zawwinnaung.fitnesstracker.data.mapper.toDomain
import com.zawwinnaung.fitnesstracker.data.remote.ApiService
import com.zawwinnaung.fitnesstracker.domain.model.User
import com.zawwinnaung.fitnesstracker.domain.result.NetworkResult
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: ApiService
) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): NetworkResult<User> {
        return try {
            val response = api.login(email, password)
            if (response.status == "success" && response.data != null) {
                NetworkResult.Success(response.data.toDomain(), response.message)
            } else {
                NetworkResult.Error(response.message)
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown Error")
        }
    }
}