package com.zawwinnaung.fitnesstracker.ui.screen.updateprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zawwinnaung.fitnesstracker.domain.result.NetworkResult
import com.zawwinnaung.fitnesstracker.domain.usecase.UpdateProfileUseCase
import com.zawwinnaung.fitnesstracker.util.UserSessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

data class UpdateProfileUiState(
    val dob: String = "",
    val sex: String = "",
    val initialDob: String = "",
    val initialSex: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,
    val successMessage: String? = null
) {
    val isChanged: Boolean
        get() = dob != initialDob || sex != initialSex
}

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val userSessionManager: UserSessionManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(UpdateProfileUiState())
    val uiState: StateFlow<UpdateProfileUiState> = _uiState.asStateFlow()

    init {
        loadInitialProfileData()
    }

    private fun loadInitialProfileData() {
        val currentUser = userSessionManager.getUser()
        val initialDob = currentUser.dateOfBirth
        val initialSex = currentUser.genderDisplay

        _uiState.update {
            it.copy(
                dob = initialDob,
                sex = initialSex,
                initialDob = initialDob,
                initialSex = initialSex
            )
        }
    }

    fun onDateSelected(millis: Long?) {
        if (millis == null) return

        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        val formattedDate = formatter.format(Date(millis))

        _uiState.update { it.copy(dob = formattedDate, errorMessage = null) }
    }

    fun onSexChanged(newSex: String) {
        _uiState.update { it.copy(sex = newSex, errorMessage = null) }
    }

    fun updateProfile() {
        val currentState = _uiState.value

        if (!currentState.isChanged) {
            _uiState.update { it.copy(errorMessage = "No changes detected to update.") }
            return
        }

        if (currentState.dob.isBlank() && currentState.sex.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Please update at least DOB or Gender") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val mappedSex = when (currentState.sex.lowercase()) {
                "male" -> "M"
                "female" -> "F"
                else -> "O"
            }
            when (val result = updateProfileUseCase(currentState.dob, mappedSex)) {
                is NetworkResult.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isSuccess = true,
                            successMessage = result.message
                        )
                    }
                }

                is NetworkResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, errorMessage = result.message) }
                }
            }
        }
    }
}