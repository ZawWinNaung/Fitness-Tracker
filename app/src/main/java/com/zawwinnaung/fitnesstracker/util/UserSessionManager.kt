package com.zawwinnaung.fitnesstracker.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.zawwinnaung.fitnesstracker.domain.model.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart

class UserSessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun saveUser(
        user: User
    ) {
        prefs.edit().apply {
            putInt("user_id", user.id)
            putString("user_name", user.userName)
            putString("email", user.email)
            putString("dob", user.dateOfBirth)
            putString("sex", user.gender)
            putBoolean("is_logged_in", true)
            apply()
        }
    }

    val isLoggedInFlow: Flow<Boolean> = callbackFlow {
        val listener =
            SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
                if (key == "is_logged_in") {
                    trySend(sharedPreferences.getBoolean("is_logged_in", false))
                }
            }
        prefs.registerOnSharedPreferenceChangeListener(listener)
        awaitClose { prefs.unregisterOnSharedPreferenceChangeListener(listener) }
    }.onStart { emit(isLoggedIn()) }

    fun isLoggedIn(): Boolean = prefs.getBoolean("is_logged_in", false)

    fun clearSession() {
        prefs.edit { clear() }
    }
}