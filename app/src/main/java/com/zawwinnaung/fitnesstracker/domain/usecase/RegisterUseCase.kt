package com.zawwinnaung.fitnesstracker.domain.usecase

import com.zawwinnaung.fitnesstracker.data.repository.ApiRepository
import com.zawwinnaung.fitnesstracker.domain.model.RegisterRequest
import com.zawwinnaung.fitnesstracker.domain.result.NetworkResult
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: ApiRepository
) {
    suspend operator fun invoke(request: RegisterRequest): NetworkResult<Unit> {
        return repository.register(request)
    }
}