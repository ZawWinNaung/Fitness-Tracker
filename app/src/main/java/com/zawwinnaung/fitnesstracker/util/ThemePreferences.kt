package com.zawwinnaung.fitnesstracker.util

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import androidx.core.content.edit

class ThemePreferences(context: Context) {

    companion object {
        private const val APP_THEME = "app_theme"
        private const val IS_DARK_MODE = "is_dark_mode"
    }

    private val prefs = context.getSharedPreferences(APP_THEME, Context.MODE_PRIVATE)

    val isDarkThemeFlow: Flow<Boolean> = callbackFlow {
        val listener =
            SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
                if (key == IS_DARK_MODE) {
                    trySend(sharedPreferences.getBoolean(key, false))
                }
            }
        prefs.registerOnSharedPreferenceChangeListener(listener)
        trySend(prefs.getBoolean(IS_DARK_MODE, false))
        awaitClose { prefs.unregisterOnSharedPreferenceChangeListener(listener) }
    }

    fun isDarkMode(): Boolean = prefs.getBoolean(IS_DARK_MODE, false)

    fun setDarkTheme(isDark: Boolean) {
        prefs.edit { putBoolean(IS_DARK_MODE, isDark) }
    }
}