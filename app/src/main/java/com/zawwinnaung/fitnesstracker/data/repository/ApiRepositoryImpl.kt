package com.zawwinnaung.fitnesstracker.data.repository

import com.zawwinnaung.fitnesstracker.data.mapper.toDomain
import com.zawwinnaung.fitnesstracker.data.remote.ApiService
import com.zawwinnaung.fitnesstracker.data.repository.base.BaseRepository
import com.zawwinnaung.fitnesstracker.domain.model.RegisterRequest
import com.zawwinnaung.fitnesstracker.domain.model.User
import com.zawwinnaung.fitnesstracker.domain.result.NetworkResult
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val api: ApiService
) : ApiRepository, BaseRepository() {
    override suspend fun login(
        email: String,
        password: String
    ): NetworkResult<User> {
        return safeApiCall {
            api.login(email, password)
        }.let { result ->
            when (result) {
                is NetworkResult.Success -> {
                    NetworkResult.Success(result.data.toDomain(), result.message)
                }

                is NetworkResult.Error -> {
                    NetworkResult.Error(result.message)
                }
            }
        }
    }

    override suspend fun register(request: RegisterRequest): NetworkResult<Unit> {
        return safeApiCall {
            api.register(
                userName = request.userName,
                email = request.email,
                password = request.password
            )
        }
    }

    override suspend fun updateProfile(
        userId: Int,
        dob: String,
        sex: String
    ): NetworkResult<User> {
        return safeApiCall {
            api.updateProfile(userId, dob, sex)
        }.let { result ->
            when (result) {
                is NetworkResult.Success -> {
                    NetworkResult.Success(result.data.toDomain(), result.message)
                }

                is NetworkResult.Error -> {
                    NetworkResult.Error(result.message)
                }
            }
        }
    }

    override suspend fun getUser(userId: Int): NetworkResult<User> {
        return safeApiCall {
            api.getUser(userId)
        }.let { result ->
            when (result) {
                is NetworkResult.Success -> {
                    NetworkResult.Success(result.data.toDomain(), result.message)
                }

                is NetworkResult.Error -> {
                    NetworkResult.Error(result.message)
                }
            }
        }
    }
}