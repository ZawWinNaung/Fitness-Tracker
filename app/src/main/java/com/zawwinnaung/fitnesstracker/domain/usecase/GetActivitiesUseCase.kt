package com.zawwinnaung.fitnesstracker.domain.usecase

import com.zawwinnaung.fitnesstracker.data.repository.ApiRepository
import com.zawwinnaung.fitnesstracker.data.repository.DbRepository
import com.zawwinnaung.fitnesstracker.domain.model.Activity
import com.zawwinnaung.fitnesstracker.domain.result.NetworkResult
import javax.inject.Inject

class GetActivitiesUseCase @Inject constructor(
    private val repository: ApiRepository,
    private val dbRepository: DbRepository,
) {
    suspend operator fun invoke(): NetworkResult<List<Activity>> {
        val result = repository.getActivities()

        return if (result is NetworkResult.Success) {
            dbRepository.cacheActivities(result.data)
            result
        } else {
            val cachedData = dbRepository.getCachedActivities()

            if (cachedData.isNotEmpty()) {
                NetworkResult.Success(data = cachedData, message = "")
            } else {
                result
            }
        }
    }
}