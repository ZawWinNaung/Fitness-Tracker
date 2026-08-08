package com.zawwinnaung.fitnesstracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zawwinnaung.fitnesstracker.data.local.dao.ActivityDao
import com.zawwinnaung.fitnesstracker.data.local.dao.TrackedActivityDao
import com.zawwinnaung.fitnesstracker.data.local.entity.ActivityEntity
import com.zawwinnaung.fitnesstracker.data.local.entity.TrackedActivitiesEntity

@Database(
    entities = [ActivityEntity::class, TrackedActivitiesEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoutePointConverter::class)
abstract class FitnessTrackerDatabase : RoomDatabase() {
    abstract fun activityDao(): ActivityDao
    abstract fun trackedActivityDao(): TrackedActivityDao
}