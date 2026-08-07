package com.zawwinnaung.fitnesstracker.ui.screen.trackingsummary

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import com.zawwinnaung.fitnesstracker.domain.model.TrackedActivity
import com.zawwinnaung.fitnesstracker.domain.model.toLatLngList
import com.zawwinnaung.fitnesstracker.ui.components.AppCard
import com.zawwinnaung.fitnesstracker.ui.components.MyTopAppBar
import com.zawwinnaung.fitnesstracker.util.formatLongToReadableTime

@Composable
fun TrackingSummaryScreen(
    trackedActivity: TrackedActivity,
    onNavigateBack: () -> Unit
) {
    val cameraPositionState = rememberCameraPositionState()
    val coroutineScope = rememberCoroutineScope()
    var isMapLoaded by remember { mutableStateOf(false) }
//    val uniqueRoutes = trackedActivity.routes.toLatLngList().distinct()
    val uniqueRoutes = listOf(
        LatLng(16.8409383, 96.173525),
        LatLng(16.8459383, 96.178525)
    )

    LaunchedEffect(isMapLoaded, uniqueRoutes) {
        if (isMapLoaded && uniqueRoutes.isNotEmpty()) {
            if (uniqueRoutes.size > 1) {
                val boundsBuilder = LatLngBounds.Builder()
                uniqueRoutes.forEach { point ->
                    boundsBuilder.include(point)
                }
                cameraPositionState.animate(
                    CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 100)
                )
            } else {
                cameraPositionState.animate(
                    CameraUpdateFactory.newLatLngZoom(uniqueRoutes.first(), 16f)
                )
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        MyTopAppBar(
            title = "",
            onNavigationIconClick = {
                onNavigateBack()
            }
        )
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                contentScale = ContentScale.Fit,
                painter = painterResource(id = trackedActivity.activity.img),
                contentDescription = trackedActivity.activity.title,
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Workout Summary",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = trackedActivity.activity.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    text = "Awesome job! You worked out for ${
                        formatLongToReadableTime(
                            trackedActivity.time
                        )
                    }",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.SemiBold
                )

                AppCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(top = 16.dp),
                    shape = RoundedCornerShape(40.dp)
                ) {
                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState,
                        onMapLoaded = {
                            isMapLoaded = true
                        }
                    ) {
                        if (uniqueRoutes.size > 1) {
                            Polyline(
                                points = uniqueRoutes,
                                color = MaterialTheme.colorScheme.primary,
                                width = 12f
                            )
                        } else if (uniqueRoutes.size == 1) {
                            Marker(
                                state = rememberUpdatedMarkerState(position = uniqueRoutes.first()),
                                title = "Workout Location"
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // Discard / Delete Button
                    Button(
                        modifier = Modifier.weight(0.5f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.onErrorContainer
                        ),
                        onClick = onNavigateBack
                    ) {
                        Text(
                            text = "Discard",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    // Save Record Button
                    Button(
                        modifier = Modifier.weight(0.5f),
                        onClick = {}
                    ) {
                        Text(
                            text = "Save",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}