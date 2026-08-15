package com.zawwinnaung.fitnesstracker.data.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.zawwinnaung.fitnesstracker.R
import com.zawwinnaung.fitnesstracker.domain.model.RoutePoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds
import android.content.pm.ServiceInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.core.app.ServiceCompat
import kotlin.getValue

@AndroidEntryPoint
class TrackerService : LifecycleService(), SensorEventListener {
    @Inject
    lateinit var fusedLocationClient: FusedLocationProviderClient
    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val sensorManager by lazy { getSystemService(SENSOR_SERVICE) as SensorManager }
    private var stepCounterSensor: Sensor? = null
    private var initialStepCount = -1f

    companion object {
        val trackingState = MutableStateFlow(false)
        val elapsedTime = MutableStateFlow(0L)
        val routePoints = MutableStateFlow<List<RoutePoint>>(emptyList())
        val currentSteps = MutableStateFlow(0)

        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
        const val EXTRA_ACTIVITY_TYPE = "EXTRA_ACTIVITY_TYPE"
    }

    override fun onCreate() {
        super.onCreate()
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            result.locations.forEach { location ->
                val newPoint = RoutePoint(
                    latitude = location.latitude,
                    longitude = location.longitude
                )
                routePoints.value += newPoint
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        elapsedTime.value = 0L
        routePoints.value = emptyList()
        currentSteps.value = 0
        when (intent?.action) {
            ACTION_START -> {
                val currentActivityType = intent.getStringExtra(EXTRA_ACTIVITY_TYPE) ?: "Walking"
                startForegroundService()
                startTimer()
                startLocationUpdates()
                if (currentActivityType.equals("Running", ignoreCase = true) ||
                    currentActivityType.equals("Walking", ignoreCase = true)
                ) {
                    startStepCounter()
                }
            }

            ACTION_STOP -> {
                stopForegroundService()
            }
        }
        return START_STICKY
    }

    private fun startForegroundService() {
        val channelId = "tracker_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Fitness Tracker",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Workout in Progress")
            .setContentText("Tracking your active route...")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .build()

        val foregroundServiceType =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION or ServiceInfo.FOREGROUND_SERVICE_TYPE_HEALTH
            } else {
                0
            }

        ServiceCompat.startForeground(
            this,
            1,
            notification,
            foregroundServiceType
        )

        trackingState.value = true
    }

    private fun startTimer() {
        serviceScope.launch {
            while (trackingState.value) {
                delay(1000L.milliseconds)
                elapsedTime.value++
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 4000L)
            .setMinUpdateIntervalMillis(2000L)
            .build()
        fusedLocationClient.requestLocationUpdates(
            request,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun startStepCounter() {
        stepCounterSensor?.let { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_STEP_DETECTOR) {
            currentSteps.value += 1
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    private fun stopForegroundService() {
        trackingState.value = false
        fusedLocationClient.removeLocationUpdates(locationCallback)
        sensorManager.unregisterListener(this)
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}