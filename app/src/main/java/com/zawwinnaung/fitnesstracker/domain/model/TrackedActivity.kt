package com.zawwinnaung.fitnesstracker.domain.model

import com.google.android.gms.maps.model.LatLng
import kotlinx.serialization.Serializable

@Serializable
data class TrackedActivity(
    val id: Int = 0,
    val activity: Activity,
    val time: Long,
    val routes: List<RoutePoint>,
)

fun RoutePoint.toLatLng(): LatLng {
    return LatLng(this.latitude, this.longitude)
}

fun List<RoutePoint>.toLatLngList(): List<LatLng> {
    return this.map { it.toLatLng() }
}