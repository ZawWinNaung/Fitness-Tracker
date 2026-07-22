package com.zawwinnaung.fitnesstracker.domain.usecase

import com.zawwinnaung.fitnesstracker.data.repository.ApiRepository
import com.zawwinnaung.fitnesstracker.domain.model.User
import com.zawwinnaung.fitnesstracker.domain.result.NetworkResult
import com.zawwinnaung.fitnesstracker.util.UserSessionManager
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: ApiRepository,
    private val userSessionManager: UserSessionManager
) {
    suspend operator fun invoke(userId: Int): NetworkResult<User> {
        val result = repository.getUser(userId)

        if (result is NetworkResult.Success) {
            val fetchedUser = result.data
            val currentUser = userSessionManager.getUser()
            if (fetchedUser != currentUser) {
                userSessionManager.saveUser(fetchedUser)
            }
        }

        return result
    }
}