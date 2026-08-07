package com.zawwinnaung.fitnesstracker.di

import android.content.Context
import androidx.room.Room
import com.zawwinnaung.fitnesstracker.data.local.FitnessTrackerDatabase
import com.zawwinnaung.fitnesstracker.data.local.dao.ActivityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideFitnessTrackerDatabase(
        @ApplicationContext context: Context
    ): FitnessTrackerDatabase {
        return Room.databaseBuilder(
            context,
            FitnessTrackerDatabase::class.java,
            "fitness_tracker_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideActivityDao(
        database: FitnessTrackerDatabase
    ): ActivityDao {
        return database.activityDao()
    }
}