package com.zawwinnaung.fitnesstracker.domain.usecase

import com.zawwinnaung.fitnesstracker.data.repository.ApiRepository
import com.zawwinnaung.fitnesstracker.domain.model.User
import com.zawwinnaung.fitnesstracker.domain.result.NetworkResult
import com.zawwinnaung.fitnesstracker.util.UserSessionManager
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val repository: ApiRepository,
    private val userSessionManager: UserSessionManager
) {
    suspend operator fun invoke(dob: String, sex: String): NetworkResult<User> {
        val result = repository.updateProfile(userSessionManager.getUser().id, dob, sex)

        if (result is NetworkResult.Success) {
            userSessionManager.saveUser(result.data)
        }

        return result
    }
}