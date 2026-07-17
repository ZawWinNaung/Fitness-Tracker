package com.zawwinnaung.fitnesstracker.screen.root

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zawwinnaung.fitnesstracker.util.UserSessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NavigationRootViewModel @Inject constructor(
    private val userSessionManager: UserSessionManager
) : ViewModel() {
    val isLoggedIn: StateFlow<Boolean> = userSessionManager.isLoggedInFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = userSessionManager.isLoggedIn()
        )

    fun logout() {
        userSessionManager.clearSession()
    }
}