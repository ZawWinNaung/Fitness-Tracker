package com.zawwinnaung.fitnesstracker.domain.usecase

import com.zawwinnaung.fitnesstracker.data.repository.ApiRepository
import com.zawwinnaung.fitnesstracker.domain.model.User
import com.zawwinnaung.fitnesstracker.domain.result.NetworkResult
import com.zawwinnaung.fitnesstracker.util.UserSessionManager
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: ApiRepository,
    private val userSessionManager: UserSessionManager
) {
    suspend operator fun invoke(email: String, password: String): NetworkResult<User> {
        val result = repository.login(email, password)

        if (result is NetworkResult.Success) {
            userSessionManager.saveUser(result.data)
        }

        return result
    }
}