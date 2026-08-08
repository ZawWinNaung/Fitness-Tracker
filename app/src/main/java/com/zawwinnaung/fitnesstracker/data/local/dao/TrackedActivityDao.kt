package com.zawwinnaung.fitnesstracker.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zawwinnaung.fitnesstracker.data.local.entity.TrackedActivitiesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackedActivityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(activity: TrackedActivitiesEntity)

    @Delete
    suspend fun deleteActivity(activity: TrackedActivitiesEntity)

    @Query("SELECT * FROM tracked_activities ORDER BY timestamp DESC")
    fun getAllActivities(): Flow<List<TrackedActivitiesEntity>>

    @Query("SELECT * FROM tracked_activities WHERE id = :id")
    suspend fun getActivityById(id: Int): TrackedActivitiesEntity?

    @Query("SELECT * FROM tracked_activities WHERE timestamp BETWEEN :startDate AND :endDate ORDER BY timestamp DESC")
    fun getActivitiesByDateRange(
        startDate: Long,
        endDate: Long
    ): Flow<List<TrackedActivitiesEntity>>
}