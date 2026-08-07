package com.zawwinnaung.fitnesstracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity(tableName = "tracked_activities")
data class TrackedActivitiesEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val img: Int,
    val elapsedTime: Long,
    val routePoints: List<LatLng>
)