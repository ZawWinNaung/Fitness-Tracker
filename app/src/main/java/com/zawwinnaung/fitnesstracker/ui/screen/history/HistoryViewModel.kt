package com.zawwinnaung.fitnesstracker.ui.screen.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zawwinnaung.fitnesstracker.domain.model.TrackedActivity
import com.zawwinnaung.fitnesstracker.domain.usecase.GetAllTrackedActivitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HistoryUiState(
    val trackedActivities: List<TrackedActivity> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,
)

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getAllTrackedActivitiesUseCase: GetAllTrackedActivitiesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    init {
        getAllTrackedActivities()
    }

    fun getAllTrackedActivities() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getAllTrackedActivitiesUseCase.invoke().catch { e ->
                _uiState.update {
                    it.copy(
                        errorMessage = e.localizedMessage ?: "Error while fetching history",
                        isLoading = false
                    )
                }
            }.collect { activities ->
                _uiState.update {
                    it.copy(
                        trackedActivities = activities,
                        isLoading = false,
                        errorMessage = null
                    )
                }
            }
        }
    }
}