package com.zawwinnaung.fitnesstracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zawwinnaung.fitnesstracker.data.local.dao.ActivityDao
import com.zawwinnaung.fitnesstracker.data.local.entity.ActivityEntity

@Database(entities = [ActivityEntity::class], version = 1, exportSchema = false)
abstract class FitnessTrackerDatabase : RoomDatabase() {
    abstract fun activityDao(): ActivityDao
}