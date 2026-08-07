package com.zawwinnaung.fitnesstracker.data.repository

import com.zawwinnaung.fitnesstracker.data.local.dao.ActivityDao
import com.zawwinnaung.fitnesstracker.data.mapper.toDomain
import com.zawwinnaung.fitnesstracker.data.mapper.toEntity
import com.zawwinnaung.fitnesstracker.domain.model.Activity
import javax.inject.Inject

class DbRepositoryImpl @Inject constructor(
    private val activityDao: ActivityDao,
) : DbRepository {
    override suspend fun cacheActivities(activities: List<Activity>) {
        activityDao.insertActivities(activities.toEntity())
    }

    override suspend fun getCachedActivities(): List<Activity> {
        return activityDao.getAllActivities().toDomain()
    }
}