package com.zawwinnaung.fitnesstracker.ui.screen.tracking

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zawwinnaung.fitnesstracker.data.service.TrackerService
import com.zawwinnaung.fitnesstracker.domain.model.Activity
import com.zawwinnaung.fitnesstracker.domain.model.TrackedActivity
import com.zawwinnaung.fitnesstracker.ui.components.AppCard
import com.zawwinnaung.fitnesstracker.ui.components.MyTopAppBar
import com.zawwinnaung.fitnesstracker.util.formatTime

@Composable
fun TrackingScreen(
    activity: Activity,
    onNavigateBack: (Activity) -> Unit,
    onNavigateToSummary: (TrackedActivity) -> Unit,
    viewModel: TrackingViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val isTracking by viewModel.isTracking.collectAsStateWithLifecycle()
    val elapsedSeconds by viewModel.elapsedSeconds.collectAsStateWithLifecycle()
    val route by viewModel.route.collectAsStateWithLifecycle()

    val startTrackingAction = {
        val startIntent = Intent(context, TrackerService::class.java).apply {
            action = TrackerService.ACTION_START
        }
        ContextCompat.startForegroundService(context, startIntent)
    }

    val stopTrackingAction = {
        val stopIntent = Intent(context, TrackerService::class.java).apply {
            action = TrackerService.ACTION_STOP
        }
        context.startService(stopIntent)
        val trackedActivity = viewModel.createTrackedActivity(activity)
        onNavigateToSummary(trackedActivity)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        MyTopAppBar(
            title = "",
            onNavigationIconClick = {
                onNavigateBack(activity)
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
                painter = painterResource(id = activity.img),
                contentDescription = activity.title,
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Current Session",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = activity.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontStyle = FontStyle.Italic
                )

                AppCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(top = 100.dp),
                    shape = RoundedCornerShape(40.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp, horizontal = 16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = formatTime(elapsedSeconds),
                            style = MaterialTheme.typography.displayLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.SemiBold
                        )

                        Button(
                            modifier = Modifier.padding(horizontal = 16.dp).padding(top = 24.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isTracking) MaterialTheme.colorScheme.errorContainer
                                else MaterialTheme.colorScheme.primary,
                                contentColor = if (isTracking) MaterialTheme.colorScheme.onErrorContainer
                                else MaterialTheme.colorScheme.onPrimary
                            ),
                            onClick = {
                                if (isTracking) {
                                    stopTrackingAction()
                                } else {
                                    startTrackingAction()
                                }
                            }
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = if (isTracking) Icons.Default.Stop else Icons.Default.PlayArrow,
                                    contentDescription = if (isTracking) "Stop" else "Start"
                                )
                                Text(
                                    text = if (isTracking) "Stop" else "Start",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }

                }
            }
        }

    }
}