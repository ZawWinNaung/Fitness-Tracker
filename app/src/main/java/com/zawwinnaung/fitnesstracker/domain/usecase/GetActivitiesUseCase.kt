package com.zawwinnaung.fitnesstracker.domain.usecase

import com.zawwinnaung.fitnesstracker.data.repository.ApiRepository
import com.zawwinnaung.fitnesstracker.domain.model.Activity
import com.zawwinnaung.fitnesstracker.domain.result.NetworkResult
import javax.inject.Inject

class GetActivitiesUseCase @Inject constructor(
    private val repository: ApiRepository,
) {
    suspend operator fun invoke(): NetworkResult<List<Activity>> {
        val result = repository.getActivities()
        return result
    }
}