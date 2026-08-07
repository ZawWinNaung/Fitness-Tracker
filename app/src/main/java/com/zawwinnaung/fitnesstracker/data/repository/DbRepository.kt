package com.zawwinnaung.fitnesstracker.data.repository

import com.zawwinnaung.fitnesstracker.domain.model.Activity

interface DbRepository {
    suspend fun cacheActivities(activities: List<Activity>)

    suspend fun getCachedActivities(): List<Activity>
}