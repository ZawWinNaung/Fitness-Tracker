package com.zawwinnaung.fitnesstracker.domain.usecase

import com.zawwinnaung.fitnesstracker.data.repository.DbRepository
import com.zawwinnaung.fitnesstracker.domain.model.Activity
import com.zawwinnaung.fitnesstracker.domain.model.TrackedActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllTrackedActivitiesUseCase @Inject constructor(
    private val dbRepository: DbRepository
) {
    suspend operator fun invoke(): Flow<List<TrackedActivity>> {
        return dbRepository.getAllTrackedActivities().map { entities ->
            entities.map { entity ->
                TrackedActivity(
                    id = entity.id,
                    activity = Activity(
                        id = entity.activityId,
                        title = entity.title,
                        img = entity.img
                    ),
                    steps = entity.steps,
                    time = entity.elapsedTime,
                    routes = entity.routePoints,
                    timestamp = entity.timestamp
                )
            }
        }
    }
}