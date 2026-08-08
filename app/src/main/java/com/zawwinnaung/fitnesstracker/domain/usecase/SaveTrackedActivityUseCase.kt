package com.zawwinnaung.fitnesstracker.domain.usecase

import com.zawwinnaung.fitnesstracker.data.local.entity.TrackedActivitiesEntity
import com.zawwinnaung.fitnesstracker.data.repository.DbRepository
import com.zawwinnaung.fitnesstracker.domain.model.TrackedActivity
import javax.inject.Inject

class SaveTrackedActivityUseCase @Inject constructor(
    private val dbRepository: DbRepository
) {
    suspend operator fun invoke(trackedActivity: TrackedActivity) {
        try {
            val entity = TrackedActivitiesEntity(
                activityId = trackedActivity.activity.id,
                title = trackedActivity.activity.title,
                img = trackedActivity.activity.img,
                elapsedTime = trackedActivity.time,
                routePoints = trackedActivity.routes
            )
            dbRepository.saveTrackedActivity(entity)
        } catch (e: Exception) {
            print(e)
        }
    }
}