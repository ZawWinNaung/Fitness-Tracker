package com.zawwinnaung.fitnesstracker.data.repository

import com.zawwinnaung.fitnesstracker.data.local.dao.ActivityDao
import com.zawwinnaung.fitnesstracker.data.local.dao.TrackedActivityDao
import com.zawwinnaung.fitnesstracker.data.local.entity.TrackedActivitiesEntity
import com.zawwinnaung.fitnesstracker.data.mapper.toDomain
import com.zawwinnaung.fitnesstracker.data.mapper.toEntity
import com.zawwinnaung.fitnesstracker.domain.model.Activity
import com.zawwinnaung.fitnesstracker.domain.model.TrackedActivity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DbRepositoryImpl @Inject constructor(
    private val activityDao: ActivityDao,
    private val trackedActivityDao: TrackedActivityDao
) : DbRepository {
    override suspend fun cacheActivities(activities: List<Activity>) {
        activityDao.insertActivities(activities.toEntity())
    }

    override suspend fun getCachedActivities(): List<Activity> {
        return activityDao.getAllActivities().toDomain()
    }

    override suspend fun saveTrackedActivity(trackedActivity: TrackedActivitiesEntity) {
        trackedActivityDao.insertActivity(trackedActivity)
    }

    override suspend fun getAllTrackedActivities(): Flow<List<TrackedActivitiesEntity>> {
        return trackedActivityDao.getAllActivities()
    }
}