package com.zawwinnaung.fitnesstracker.ui.screen.trackingsummary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zawwinnaung.fitnesstracker.domain.model.TrackedActivity
import com.zawwinnaung.fitnesstracker.domain.usecase.SaveTrackedActivityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackingSummaryViewModel @Inject constructor(
    private val saveTrackedActivityUseCase: SaveTrackedActivityUseCase
) : ViewModel() {
    fun saveTrackedActivity(
        trackedActivity: TrackedActivity,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                saveTrackedActivityUseCase.invoke(trackedActivity)
                onSuccess("Saved Activity Successfully")
            } catch (e: Exception) {
                onError(e.localizedMessage ?: "Unknown Error")
            }
        }
    }
}