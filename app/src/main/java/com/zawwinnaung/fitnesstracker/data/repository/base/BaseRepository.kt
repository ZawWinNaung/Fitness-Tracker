package com.zawwinnaung.fitnesstracker.data.repository.base

import com.zawwinnaung.fitnesstracker.data.dto.BaseResponse
import com.zawwinnaung.fitnesstracker.domain.result.NetworkResult

abstract class BaseRepository {
    suspend fun <T> safeApiCall(apiCall: suspend () -> BaseResponse<T>): NetworkResult<T> {
        return try {
            val response = apiCall()
            if (response.status == "success") {
                NetworkResult.Success(response.data as T, response.message)
            } else {
                NetworkResult.Error(response.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            NetworkResult.Error(e.message ?: "Unknown Error")
        }
    }
}