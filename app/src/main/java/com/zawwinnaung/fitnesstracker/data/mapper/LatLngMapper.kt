package com.zawwinnaung.fitnesstracker.data.mapper

import com.google.android.gms.maps.model.LatLng
import com.zawwinnaung.fitnesstracker.domain.model.RoutePoint

fun RoutePoint.toLatLng(): LatLng {
    return LatLng(this.latitude, this.longitude)
}

fun LatLng.toRoutePoint(): RoutePoint {
    return RoutePoint(latitude = this.latitude, longitude = this.longitude)
}

fun List<RoutePoint>.toLatLngList(): List<LatLng> {
    return this.map { it.toLatLng() }
}

fun List<LatLng>.toRoutePointList(): List<RoutePoint> {
    return this.map { it.toRoutePoint() }
}