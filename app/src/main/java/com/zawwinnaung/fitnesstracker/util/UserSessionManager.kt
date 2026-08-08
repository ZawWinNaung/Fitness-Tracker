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

    companion object {
        private const val PREF_NAME = "user_session"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_EMAIL = "email"
        private const val KEY_DOB = "dob"
        private const val KEY_SEX = "sex"
    }

    private val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveUser(user: User) {
        prefs.edit().apply {
            putInt(KEY_USER_ID, user.id)
            putString(KEY_USER_NAME, user.userName)
            putString(KEY_EMAIL, user.email)
            putString(KEY_DOB, user.dateOfBirth)
            putString(KEY_SEX, user.gender)
            putBoolean(KEY_IS_LOGGED_IN, true)
            apply()
        }
    }

    val isLoggedInFlow: Flow<Boolean> = callbackFlow {
        val listener =
            SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
                if (key == KEY_IS_LOGGED_IN) {
                    trySend(sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false))
                }
            }
        prefs.registerOnSharedPreferenceChangeListener(listener)
        awaitClose { prefs.unregisterOnSharedPreferenceChangeListener(listener) }
    }.onStart { emit(isLoggedIn()) }

    fun isLoggedIn(): Boolean = prefs.getBoolean(KEY_IS_LOGGED_IN, false)

    fun getUser(): User {
        return User(
            id = prefs.getInt(KEY_USER_ID, 0),
            userName = prefs.getString(KEY_USER_NAME, "") ?: "",
            email = prefs.getString(KEY_EMAIL, "") ?: "",
            dateOfBirth = prefs.getString(KEY_DOB, "") ?: "",
            gender = prefs.getString(KEY_SEX, "") ?: ""
        )
    }

    fun getUserId(): Int {
        return prefs.getInt(KEY_USER_ID, 0)
    }

    fun getUserName(): String {
        return prefs.getString(KEY_USER_NAME, "") ?: ""
    }

    fun clearSession() {
        prefs.edit().apply {
            putBoolean(KEY_IS_LOGGED_IN, false)
            remove(KEY_USER_ID)
            remove(KEY_USER_NAME)
            remove(KEY_EMAIL)
            remove(KEY_DOB)
            remove(KEY_SEX)
            apply()
        }
    }
}