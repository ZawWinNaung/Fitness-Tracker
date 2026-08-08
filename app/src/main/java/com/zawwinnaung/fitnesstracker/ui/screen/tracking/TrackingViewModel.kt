package com.zawwinnaung.fitnesstracker.ui.screen.tracking

import android.R.attr.action
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.zawwinnaung.fitnesstracker.data.service.TrackerService
import com.zawwinnaung.fitnesstracker.domain.model.Activity
import com.zawwinnaung.fitnesstracker.domain.model.TrackedActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrackingViewModel @Inject constructor() : ViewModel() {
    val trackerService = TrackerService
    val isTracking = trackerService.trackingState
    val elapsedSeconds = trackerService.elapsedTime
    val route = trackerService.routePoints

    fun createTrackedActivity(activity: Activity): TrackedActivity {
        return TrackedActivity(
            activity = activity,
            time = elapsedSeconds.value,
            routes = route.value
        )
    }
}