package com.zawwinnaung.fitnesstracker.screen.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zawwinnaung.fitnesstracker.domain.model.RegisterRequest
import com.zawwinnaung.fitnesstracker.domain.result.NetworkResult
import com.zawwinnaung.fitnesstracker.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    var isLoading by mutableStateOf(false)
        private set

    var userNameError by mutableStateOf<String?>(null)
        private set
    var emailError by mutableStateOf<String?>(null)
        private set
    var passwordError by mutableStateOf<String?>(null)
        private set

    fun clearError(field: String) {
        when (field) {
            "userName" -> userNameError = null
            "email" -> emailError = null
            "password" -> passwordError = null
        }
    }

    fun register(
        userName: String,
        email: String,
        password: String,
        onResult: (Boolean, String) -> Unit
    ) {
        userNameError = null
        emailError = null
        passwordError = null

        if (userName.isBlank()) userNameError = "Username is required"
        if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches()
        ) emailError = "Invalid email"
        if (password.length < 6) passwordError = "Min 6 characters"

        if (userNameError != null || emailError != null || passwordError != null) return

        viewModelScope.launch {
            isLoading = true
            when (val result = registerUseCase(RegisterRequest(userName, email, password))) {
                is NetworkResult.Success -> onResult(true, result.message)

                is NetworkResult.Error -> onResult(false, result.message)
            }
            isLoading = false
        }
    }
}