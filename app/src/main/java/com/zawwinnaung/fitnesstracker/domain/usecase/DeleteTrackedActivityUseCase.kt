package com.zawwinnaung.fitnesstracker.domain.usecase

import com.zawwinnaung.fitnesstracker.data.repository.DbRepository
import javax.inject.Inject

class DeleteTrackedActivityUseCase @Inject constructor(
    private val dbRepository: DbRepository
) {
    suspend operator fun invoke(id: Int) {
        try {
            dbRepository.deleteTrackedActivityById(id)
        } catch (e: Exception) {
            print(e)
        }
    }
}