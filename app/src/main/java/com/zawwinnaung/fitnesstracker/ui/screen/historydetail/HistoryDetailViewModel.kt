package com.zawwinnaung.fitnesstracker.ui.screen.historydetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zawwinnaung.fitnesstracker.domain.usecase.DeleteTrackedActivityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryDetailViewModel @Inject constructor(
    private val deleteTrackedActivityUseCase: DeleteTrackedActivityUseCase
) : ViewModel() {

    fun deleteActivity(
        id: Int,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                deleteTrackedActivityUseCase.invoke(id)
                onSuccess("Record is deleted")
            } catch (e: Exception) {
                onError(e.localizedMessage ?: "Error while deleting")
            }
        }
    }
}