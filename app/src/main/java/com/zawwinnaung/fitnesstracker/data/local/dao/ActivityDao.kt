package com.zawwinnaung.fitnesstracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.zawwinnaung.fitnesstracker.data.local.entity.ActivityEntity

@Dao
interface ActivityDao {
    @Query("SELECT * FROM activities")
    suspend fun getAllActivities(): List<ActivityEntity>

    @Upsert
    suspend fun insertActivities(activities: List<ActivityEntity>)
}