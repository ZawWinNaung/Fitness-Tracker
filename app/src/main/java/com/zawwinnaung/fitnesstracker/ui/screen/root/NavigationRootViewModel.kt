package com.zawwinnaung.fitnesstracker.ui.screen.root

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zawwinnaung.fitnesstracker.util.ThemePreferences
import com.zawwinnaung.fitnesstracker.util.UserSessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NavigationRootViewModel @Inject constructor(
    private val userSessionManager: UserSessionManager,
    private val themePreferences: ThemePreferences
) : ViewModel() {
    val isLoggedIn: StateFlow<Boolean> = userSessionManager.isLoggedInFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = userSessionManager.isLoggedIn()
        )

    val isDarkTheme: StateFlow<Boolean> = themePreferences.isDarkThemeFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = themePreferences.isDarkMode()
        )

    val isDynamicColor: StateFlow<Boolean> = themePreferences.isDynamicColorFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = themePreferences.isDynamicColor()
        )
}