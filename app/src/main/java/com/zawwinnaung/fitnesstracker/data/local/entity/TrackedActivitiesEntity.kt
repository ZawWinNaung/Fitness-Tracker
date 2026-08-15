package com.zawwinnaung.fitnesstracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zawwinnaung.fitnesstracker.domain.model.RoutePoint

@Entity(tableName = "tracked_activities")
data class TrackedActivitiesEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val activityId: Int,
    val title: String,
    val img: Int,
    val steps: Int = 0,
    val elapsedTime: Long,
    val routePoints: List<RoutePoint>,
    val timestamp: Long = System.currentTimeMillis()
)