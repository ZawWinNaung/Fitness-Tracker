package com.zawwinnaung.fitnesstracker.di

import android.content.Context
import com.zawwinnaung.fitnesstracker.util.ThemePreferences
import com.zawwinnaung.fitnesstracker.util.UserSessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideUserSessionManager(
        @ApplicationContext context: Context
    ): UserSessionManager {
        return UserSessionManager(context)
    }

    @Provides
    @Singleton
    fun provideThemePreference(
        @ApplicationContext context: Context
    ): ThemePreferences {
        return ThemePreferences(context)
    }
}