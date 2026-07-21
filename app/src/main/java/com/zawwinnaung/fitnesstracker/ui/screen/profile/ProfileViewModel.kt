package com.zawwinnaung.fitnesstracker.ui.screen.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.zawwinnaung.fitnesstracker.domain.model.User
import com.zawwinnaung.fitnesstracker.util.ThemePreferences
import com.zawwinnaung.fitnesstracker.util.UserSessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sessionManager: UserSessionManager,
    private val themePreferences: ThemePreferences
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _isDarkTheme = MutableStateFlow(themePreferences.isDarkMode())
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    fun getUser() {
        _user.value = sessionManager.getUser()
    }

    fun logout() {
        sessionManager.clearSession()
    }

    fun toggleTheme(isDark: Boolean) {
        themePreferences.setDarkTheme(isDark)
        _isDarkTheme.value = isDark
    }
}