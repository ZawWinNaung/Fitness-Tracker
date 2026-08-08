package com.zawwinnaung.fitnesstracker.ui.screen.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch


@Composable
fun MapScreen() {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val cameraPositionState = rememberCameraPositionState()
    var isLocationEnabled by remember { mutableStateOf(false) }

    val hasPermission = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    fun checkGpsStatus(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(isMyLocationEnabled = hasPermission),
            onMapLoaded = {
                if (hasPermission) {
                    isLocationEnabled = checkGpsStatus()
                    if (isLocationEnabled) {
                        val fusedLocationClient =
                            LocationServices.getFusedLocationProviderClient(context)
                        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                            location?.let {
                                val currentLatLng = LatLng(it.latitude, it.longitude)
                                coroutineScope.launch {
                                    cameraPositionState.animate(
                                        update = CameraUpdateFactory.newLatLngZoom(
                                            currentLatLng,
                                            15f
                                        ),
                                        durationMs = 1000
                                    )
                                }
                            }
                        }
                    }
                }
            }
        )

        if (!hasPermission) {
            Text(
                text = "Location permission was denied. Enable it in settings to view nearby spots.",
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
            )
        } else if (!isLocationEnabled) {
            Text(
                text = "Please turn on your device GPS/Location.",
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
            )
        }
    }
}