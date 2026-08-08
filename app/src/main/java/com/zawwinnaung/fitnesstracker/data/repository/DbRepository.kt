package com.zawwinnaung.fitnesstracker.data.repository

import com.zawwinnaung.fitnesstracker.data.local.entity.TrackedActivitiesEntity
import com.zawwinnaung.fitnesstracker.domain.model.Activity
import kotlinx.coroutines.flow.Flow

interface DbRepository {
    suspend fun cacheActivities(activities: List<Activity>)

    suspend fun getCachedActivities(): List<Activity>

    suspend fun saveTrackedActivity(trackedActivity: TrackedActivitiesEntity)

    suspend fun getAllTrackedActivities(): Flow<List<TrackedActivitiesEntity>>

    suspend fun deleteTrackedActivityById(id: Int)
}