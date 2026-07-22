package com.zawwinnaung.fitnesstracker.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zawwinnaung.fitnesstracker.domain.model.User
import com.zawwinnaung.fitnesstracker.domain.result.NetworkResult
import com.zawwinnaung.fitnesstracker.domain.usecase.GetUserUseCase
import com.zawwinnaung.fitnesstracker.ui.screen.updateprofile.UpdateProfileUiState
import com.zawwinnaung.fitnesstracker.util.ThemePreferences
import com.zawwinnaung.fitnesstracker.util.UserSessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val sessionManager: UserSessionManager,
    private val themePreferences: ThemePreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _isDarkTheme = MutableStateFlow(themePreferences.isDarkMode())
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    fun getUser() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = getUserUseCase.invoke(sessionManager.getUserId())) {
                is NetworkResult.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null,
                            user = result.data
                        )
                    }
                }

                is NetworkResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.message,
                            user = null
                        )
                    }
                }
            }
        }
    }

    fun logout() {
        sessionManager.clearSession()
    }

    fun toggleTheme(isDark: Boolean) {
        themePreferences.setDarkTheme(isDark)
        _isDarkTheme.value = isDark
    }
}